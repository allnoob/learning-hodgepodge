//格式化时间戳
export const formatTime = (date, n) => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()
    const hour = date.getHours()
    const minute = date.getMinutes()
    const second = date.getSeconds()
    switch(n){
      case 1:
        return [year, month, day].map(formatNumber).join('-')
        break;  
      case 2:   
        return [month, day].map(formatNumber).join('-')
        break; 
      case 3:
        return [hour, minute].map(formatNumber).join(':')
        break; 
      case 4:
        return [hour].map(formatNumber)
        break; 
      case 5:
        return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute,second].map(formatNumber).join(':') 
        break;
      case 6:
        return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute].map(formatNumber).join(':') 
        break;
      case 7:
        return [hour, minute, second].map(formatNumber).join(':')
        break;
      default:
    }
  }
  