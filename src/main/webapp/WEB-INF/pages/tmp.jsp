<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
</head>
<body>
<div class="fly fly1"></div>
<div class="fly fly2"></div>
<div class="fly fly3"></div>
<div id="earth">
    <div class="radar">
        <div class="circle">
            <div class="h"></div>
            <div class="v"></div>
            <div class="scan"></div>
            <div class="dot dot1"></div>
            <div class="dot dot2"></div>
            <div class="dot dot3"></div>
            <div class="dot dot4"></div>
        </div>
        <div class="icon">
            <div class="hotspot"></div>
        </div>
    </div>
</div>
<!-- by 戈饭 -->
<style type="text/css">
    /*CSS源代码*/
    html,body{
        margin:0;
        padding: 0;
        height: 100%;
        background:#F0743E;
    }
    body {
        position: relative;
        overflow:hidden;
        -webkit-perspective: 450px;
        -moz-perspective: 450px;
        -ms-perspective: 450px;
        perspective: 450px;
        perspective-origin: 50% 50%;
    }
    #sky {
        width:100%;
        height: 50%;
        background:#F0743E;
    }
    .fly{
        position: fixed;
        top:0;
        margin: auto;
        width: 150px;
        height: 233px;
        background: url("http://sandbox.runjs.cn/uploads/rs/125/lyhpncuv/1.png") no-repeat center / cover;
        animation: fly 3s ease-in-out infinite;
        -webkit-animation: fly 3s ease-in-out infinite;
    }
    .fly1 {
        left: 0;
        right: 0;
    }
    .fly2 {
        left: -200px;
    }
    .fly3 {
        right: -200px;
    }
    #earth {
        position: absolute;
        left: -50%;
        bottom: -15%;
        margin: auto;
        width: 200%;
        height: 100%;
        background:#000;
        -webkit-transform: rotateX(45deg);
        -moz-transform: rotateX(45deg);
        -ms-transform: rotateX(45deg);
        -o-transform: rotateX(45deg);
        transform: rotateX(45deg);
        border-radius:50% 50% 0 0;
    }
    .radar {
        width: 100%;
        height: 100%;
        position: relative;
    }
    .circle {
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        margin:auto;
        width: 300px;
        height: 300px;
        border-radius:50%;
        background:-moz-repeating-radial-gradient(#00A638 3%,#000 5%,#000 25%);
        background:-webkit-repeating-radial-gradient(#00A638 3%,#000 5%,#000 25%);
        background:repeating-radial-gradient(#00A638 3%,#000 5%,#000 25%);
    }
    .h,.v {
        position: absolute;
    }
    .h {
        top: 149px;
        height:2px;
        width: 100%;
        background: #00a638;
    }
    .v {
        left: 149px;
        width: 2px;
        height: 100%;
        background:#00a638
    }
    .scan {
        width: 150px;
        height: 150px;
        background:linear-gradient(0deg,rgba(115, 255, 162, 0.0),rgba(0, 166, 56, 0.75));
        border-radius:100% 0 0 0;
        animation: scan 4s linear infinite;
        -webkit-animation: scan 4s linear infinite;
        border-right:3px solid rgba(0, 255, 86, 0.5);
        z-index:999;
    }
    .dot {
        position: absolute;
        top:20px;
        left: 20px;
        width: 7px;
        height: 7px;
        border-radius:50%;
        box-shadow:0 0 5px 3px #00FF56;
        opacity: 0;
        background: #C3FFD3;
    }
    .dot1 {
        top: 75px;
        left: 75px;
        animation: flash 4s linear infinite 3.5s;
        -webkit-animation: flash 4s linear infinite 3.5s;
    }
    .dot2 {
        top: 150px;
        left: 250px;
        animation: flash 4s linear infinite 1s;
        -webkit-animation: flash 4s linear infinite 1s;
    }
    .dot3 {
        top: 250px;
        left: 250px;
        animation: flash 4s linear infinite 1.5s;
        -webkit-animation: flash 4s linear infinite 1.5s;
    }
    .dot4 {
        top: 60px;
        left: 230px;
        animation: flash 4s linear infinite 0.5s;
        -webkit-animation: flash 4s linear infinite 0.5s;
    }
    .icon {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 25px;
        margin: auto;
        width:30px;
        height:30px;
        opacity:0.7;
        animation: jump 1s linear infinite;
        -webkit-animation: jump 1s linear infinite;
    }
    .hotspot {
        width: 0;
        height: 0;
        border-style:solid;
        border-color:#00FF56 transparent transparent transparent;
        border-width:30px 15px 15px 15px;
    }
    .hotspot::before {
        content:'';
        position: absolute;
        width:30px;
        height:25px;
        top:-15px;
        left:0;
        background:#00FF56;
        border-radius: 50%
    }
    .hotspot::after {
        content:'';
        position: absolute;
        width: 16px;
        height: 16px;
        top:-10px;
        left:7px;
        background:#000;
        border-radius: 50%
    }
    @keyframes scan {
        0% {
            transform:rotateZ(0deg);
            transform-origin:150px 150px;
        }
        100% {
            transform:rotateZ(360deg);
            transform-origin:150px 150px;
        }
    }
    @-webkit-keyframes scan {
        0% {
            transform:rotateZ(0deg);
            transform-origin:150px 150px;
        }
        100% {
            transform:rotateZ(360deg);
            transform-origin:150px 150px;
        }
    }
    @keyframes flash {
        0% {
            opacity:0;
        }
        10% {
            opacity:1;
        }
        30% {
            opacity:0;
        }
    }
    @-webkit-keyframes flash {
        0% {
            opacity:0;
        }
        10% {
            opacity:1;
        }
        30% {
            opacity:0;
        }
    }
    @keyframes jump {
        0% {
            transform:tranlateY(-10px);
        }
        50% {
            transform:translateY(-15px);
        }
    }
    @-webkit-keyframes jump {
        0% {
            transform:tranlateY(-10px);
        }
        50% {
            transform:translateY(-15px);
        }
    }
    @keyframes fly {
        0% {
            transform:translateZ(-1350px) rotateX(-45deg);
        }
        100% {
            transform:translateZ(1350px) rotateX(-90deg);
        }
    }
    @-webkit-keyframes fly {
        0% {
            transform:translateZ(-1350px) rotateX(-45deg);
        }
        100% {
            transform:translateZ(1350px) rotateX(-90deg);
        }
    }
</style>
</body>
</html>
