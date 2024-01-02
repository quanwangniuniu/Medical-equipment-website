// 添加请求拦截器
axios.interceptors.response.use(res => {
    console.log(res);
    if(res.data.code === 0 && res.code.msg === 'NOTLOGIN'){
        window.top.location.href = '/pages/login.html'
    }else{
        return res.data;
    }
}, error => {
    // 对请求错误做些什么
    return Promise.reject(error);
});
