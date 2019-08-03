import axios from 'axios'
axios.defaults.withCredentials=true;
import qs from 'qs'

export default {
  install (Vue) {
    //    install方法，向Vue实例中添加一个$http方法
    Vue.prototype.$http = axios
    Vue.$http = axios
  },
  $http: axios
}

axios.defaults.timeout = 30000; // 1分钟
axios.defaults.baseURL = "http://localhost:8081/combination/";

// 添加请求拦截器
var myInterceptor = axios.interceptors.request.use(function (config) {
  config.headers.Accept = 'application/json';    //设置请求头Accept
  config.headers['Access-Control-Allow-Origin'] = "*";
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

axios.interceptors.request.eject(myInterceptor);

const downloadUrl = data => {
  var blob = new Blob([data])
  var downloadElement = document.createElement('a');
  var href = window.URL.createObjectURL(blob); //创建下载的链接
  downloadElement.href = href;
  downloadElement.download = 'code.zip'; //下载后文件名
  document.body.appendChild(downloadElement);
  downloadElement.click(); //点击下载
  document.body.removeChild(downloadElement); //下载完成移除元素
  window.URL.revokeObjectURL(href); //释放掉blob对象
}

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
  if(response.headers && response.headers['content-type'] === 'application/octet-stream;charset=UTF-8'){
    downloadUrl(response.data);
    return;
  }
  return response.data;
}, function (error) {
  if(error.response && error.response.status){
    error.code = error.response.status
    switch (error.response.status) {
      case 400:
        error.message = '错误请求'
        break;
      case 401:
        error.message = '未授权，请重新登录'
        break;
      case 403:
        error.message = '拒绝访问'
        break;
      case 404:
        error.message = '请求错误,未找到该资源'
        break;
      case 405:
        error.message = '请求方法未允许'
        break;
      case 408:
        error.message = '请求超时'
        break;
      case 500:
        error.message = '服务器端出错'
        break;
      case 501:
        error.message = '网络未实现'
        break;
      case 502:
        error.message = '网络错误'
        break;
      case 503:
        error.message = '服务不可用'
        break;
      case 504:
        error.message = '网络超时'
        break;
      case 505:
        error.message = 'http版本不支持该请求'
        break;
      default:
        error.message = `连接错误${error.response.status}`
    }
  }
  return Promise.reject(error);
});




export const Http = axios
