# 关于钉钉JSAPI鉴权后台接口的一些问题汇总
关于在做公司Node.js编写钉钉JSAPI鉴权然后数据传给前端获取免登码的时候，遇到了不少坑，头都非常的大，在这里就来汇总整理一下遇到的问题，以防以后重复遇到。 

### 重点
* 获取jsapi_ticket

先看看钉钉官方的说明： 
> 企业开发微应用时，当jsapi_ticket未过期的时候，再次调用get_jsapi_ticket会获取到一个全新的jsapi_ticket（和旧的jsapi_ticket值不同），这个全新的jsapi_ticket过期时间是2小时。

> ISV开发微应用时，当jsapi_ticket未过期的时候，也就是两小时之内再次调用get_jsapi_ticket获取到的jsapi_ticket和老的jsapi_ticket值相同，只是过期时间延长到2小时。

> 企业应用开发中，jsapi_ticket是一个CorpSecret对应一个，所以在使用的时候需要将jsapi_ticket以CorpSecret为维度进行缓存下来（设置缓存过期时间2小时），并不需要每次都通过接口拉取。

注意了，这里告诉我们要将jsapi_ticket拿取到了后要缓存起来，这个时候我们可以选择数据库或者本地等去选择存储这个ticket的key；还需要设置缓存时间，比方我获取的时候的时间戳存进数据库，然后每次取的时候通过数据库拿到时间戳，对比当前的时间戳，如果大于两个小时的毫秒时间戳则重新获取并存入数据库，否则直接从数据库里拿。

* 获取access_token
和上面获取jsapi_ticket差不多，实现思想同理，只不过这个token呢，两个小时之内不管你怎么请求确实只会获得到同一个，但是我们可以避免请求就避免嘛。

### 接口
接口需要提供这么几个数据
```json
{
    "ticket": string, // 你懂的就是上面获取的那个ticket
    "nonce": string, // 随机字符串
    "agentId": string, // 应用标示ID
    "timeStamp": number, // 时间戳
    "corpId": string, // 企业ID
    "signature": string // sign(ticket, nonceStr, timeStamp, url)
}
```
**重点是signature，这个需要字符串key/value拼接然后再进行加密**

现在让小弟记录一下代码大致的编写：
```ts
client.ts

/**
 * 获取钉钉开发的accessToken
 */
export async function getToken() {
  const url = 'https://oapi.dingtalk.com/gettoken';
  const query = { corpid: DingtalkConfig.corpId, corpsecret: DingtalkConfig.corpSecret };
  const requestOption = { uri: url, qs: query, method: GET };
  const data = JSON.parse(await request(requestOption));
  solveResultDataEmpty(data, '无法获取accessToken');
  vaildErrCode(data, '获取accessToken失败');
  return data.access_token;
}

/**
 * 获取jsapi_ticket
 * @param accessToken
 * @returns {Promise<any>}
 */
export async function getJsapiTicket(accessToken) {
  const url = 'https://oapi.dingtalk.com/get_jsapi_ticket';
  const query = { access_token: accessToken };
  const requestOption = { uri: url, qs: query, method: GET };
  const data = JSON.parse(await request(requestOption));
  solveResultDataEmpty(data, '无法获取jsapi_ticket');
  vaildErrCode(data, '获取jsapi_ticket失败');
  return data;
}

// solveResultDataEmpty和vaildErrCode方法是自己编写弹出报错的
```
```ts
ticket.service.ts

@Injectable()
export class TicketService {

    private readonly DINGTALK_APP_TTL: number = 6600000;

    private readonly DINGTALK_APP_REFRESH_TICKET: boolean = true;

    constructor(@InjectModel('Ticket') private readonly ticketModel: Model<Ticket>) {
    }

    private async findById(_id: string): Promise<Ticket> {
        return await this.ticketModel.findById(_id);
    }

    private async updateOrInitialization(ticketDto: TicketDto): Promise<Ticket> {
        const createdTicket = new this.ticketModel(ticketDto);
        const savedTicket: Ticket = await createdTicket.save();
        Logger.log(`Requested and saved new Ticket '${savedTicket}'.`);
        return savedTicket;
    }

    private async delete(_id: string): Promise<void> {
        await this.ticketModel.findByIdAndRemove(_id);
        Logger.log(`删除ticket '${_id}'`);
    }

    public async findOrRequestNewTicket(accessToken: string): Promise<Ticket> {
        const ENV_CORPID = process.env.DINGTALK_CORPID;
        var ticket: Ticket = await this.findById(ENV_CORPID);
        var now: number = Date.now();
        const CANNOT_REFRESH_TICKET: string = '无法刷新授权码';
        if (!ticket || now - ticket.lastRequestedTime >= this.DINGTALK_APP_TTL) {
            this.delete(ENV_CORPID);
            const data: any = await getJsapiTicket(accessToken);
            const ticket: string = data.ticket;
            let ticketDto: TicketDto = new TicketDto();
            ticketDto._id = ENV_CORPID;
            ticketDto.value = ticket;
            ticketDto.lastRequestedTime = now;
            let savedTicket: Ticket = await this.updateOrInitialization(ticketDto);
            Logger.log(`Requested and saved new Ticket '${savedTicket}'.`);
            return savedTicket;
        } else {
            return ticket;
        }
    }

}
```
```js

access.token.service.ts

@Injectable()
export class AccessTokenService {

    private readonly DINGTALK_APP_TTL: number = 6600000;

    constructor(@InjectModel('AccessToken') private readonly accessTokenModel: Model<AccessToken>) {
    }

    private async findById(_id: string): Promise<AccessToken> {
        return await this.accessTokenModel.findById(_id);
    }

    private async updateOrInitialization(accessTokenDto: AccessTokenDto): Promise<AccessToken> {
        const createdAccessToken = new this.accessTokenModel(accessTokenDto);
        const savedAccessToken: AccessToken = await createdAccessToken.save();
        Logger.log(`Requested and saved new AccessToken '${savedAccessToken}'.`);
        return savedAccessToken;
    }

    private async delete(_id: string): Promise<void> {
        await this.accessTokenModel.findByIdAndRemove(_id);
        Logger.log(`删除access_token '${_id}'`);
    }

    public async findOrRequestNewAccessToken(): Promise<string> {
        const ENV_CORPID = process.env.DINGTALK_CORPID;
        var accessToken: AccessToken = await this.findById(ENV_CORPID);
        var now: number = Date.now();
        if (!accessToken || now - accessToken.lastRequestedTime >= this.DINGTALK_APP_TTL) {
            this.delete(ENV_CORPID);
            const accessToken: string = await getToken();
            let accessTokenDto: AccessTokenDto = new AccessTokenDto();
            accessTokenDto._id = ENV_CORPID;
            accessTokenDto.value = accessToken;
            accessTokenDto.lastRequestedTime = now;
            let savedAccessToken: AccessToken = await this.updateOrInitialization(accessTokenDto);
            Logger.log(`Requested and saved new AccessTOken '${savedAccessToken}'.`);
            return savedAccessToken.value;
        } else {
            return accessToken.value;
        }
    }

}
```
```ts
token.facade.ts

@Injectable()
export class TokenFacade {

  constructor(private readonly ticketService: TicketService,
              private readonly accessTokenGenerator: AccessTokenGenerator) {
  }

  public async getSignatureParameter(tokenRequest: TokenRequest): Promise<TokenVo> {
    const accessToken = await this.accessTokenGenerator.findOrRequestNewAccessToken();
    const ticket: Ticket = await this.ticketService.findOrRequestNewTicket(accessToken);
    const ticketValue = ticket.value;
    const timeStamp = Date.parse((new Date()).toString()) / 1000;
    const nonce = uuid.v1();
    const signature = await this.signUrl(ticketValue, nonce, timeStamp, tokenRequest.name);
    return {
        "ticket": ticketValue,
        "signature": signature,
        "nonce": nonce,
        "timeStamp": timeStamp,
        "corpId": DingtalkConfig.corpId,
        "agentId": DingtalkConfig.agentId
    }
  }

  private async getJsapiUrl(): Promise<string> {
    return process.env.DINGTALK_HOST;
  }

  private async signUrl(ticket: string, nonce: string, timeStamp: number, name: string): Promise<string> {
    let urlToSign = await this.getJsapiUrl();
    if (name !== 'index') {
      urlToSign = urlToSign + name + '.html';
    }
    let plainTex = `jsapi_ticket=${ticket}&noncestr=${nonce}&timestamp=${timeStamp}&url=${urlToSign}`;
    let signature = CryptoJS.SHA1(plainTex).toString();
    Logger.log(`根据连接串 '${plainTex}' 生成签名 '${signature}'`)
    return signature;
  }

}
```