# 路由拦截
> 路由拦截对于一个后台来说是一个很重要的功能，假设说有一个权限低的账号想通过路由地址进入高权限的页面，这时候就需要使用到路由拦截进行阻拦了。

# 说明
假设有这么一个页面XXX，需要进行路由拦截
```js
{
        path: '/XXX',
        component: XXX,
        meta: [''],
        name: '',
      },
```
我们可以通过[beforeEnter](https://router.vuejs.org/zh/guide/advanced/navigation-guards.html#%E8%B7%AF%E7%94%B1%E7%8B%AC%E4%BA%AB%E7%9A%84%E5%AE%88%E5%8D%AB)方法，在进入路由之前进行判断，将其拦截。
```js
{
        path: '/XXX',
        component: XXX,
        meta: [''],
        name: '',
        beforeEnter: (to, from, next) => {
          if () {//写入判断权限的方式
            next();
          } else {//若权限不足，则跳入401页面
            next({
              path: '/401'
            })
            console.log('您的权限不足，请联系管理员')
          }
        }
      },
```