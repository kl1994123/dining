//系统层面的一些功能

function cleanTab(i) {
    layui.use(['jquery'], function(){
        var $=layui.$
        $(".layui-tab-title").find("li").eq(i).nextAll().remove();
    })
}

function flush() {
    layui.use(['jquery'], function(){
        var $=layui.$
        $('#content_val').attr('src', $('#content_val').attr('src'));
    })
}

function foward() {
    document.getElementById('content_val').contentWindow.history.forward()
}

function back() {
    document.getElementById('content_val').contentWindow.history.back()
}

function reload() {
    location.reload()
}

function gotoTab(name,url) {
    layui.use(['element','jquery'], function(){
        var element,$
        element = layui.element
        $=layui.$
        addTab(name,url)
        $("#content_val").attr("src",url)
    });
}

function addTab(name,url){
    layui.use(['element','jquery'], function(){
        var element,$
        element = layui.element
        $=layui.$
        var content='<li urlVal="'+url+'">'+name+'</li>'
        $(".layui-tab-title").append(content)
    });
}

function goto(url) {
    layui.use(['element','jquery'], function(){
        var element,$
        element = layui.element
        $=layui.$
        $("#content_val").attr("src",url)
    });
}