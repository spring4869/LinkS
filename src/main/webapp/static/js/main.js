window.onload = function () {
    // 顶部图片随鼠标移动动画
    let bg = document.getElementById("header")
    bg.onmouseenter = (e) => {

        let enterX = e.clientX;

        let cliX = parseInt(bg.style.backgroundPosition.split(" ")[0]) || 0;

        bg.onmousemove = (e) => {
            let currX = cliX + (e.clientX - enterX) * 0.2;

            bg.style['background-position'] = `${currX}px`;

        }
    }

// 获取用户的所有属性
    new Vue({
        el:".profile",
        data(){
            return{
                userInfo:null,
            }
        },
        mounted(){
            axios.post("user/getUserInfo").then(Response=>{
                this.userInfo=Response.data
            })
        }
    });
}



// 以下无需window.onload()的函数


// 右上角转跳登录界面的功能
function redirectLogin(){
    if($(".profile-name").html()==="Welcome"){
        window.location="login"
    }
}
