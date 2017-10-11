<style>
   /* html {
        font-weight:normal;
        position: relative;
        height: 100%;
    }*/

    a {
        text-decoration: none;
    }
    img {
        border-style: none;
    }
    ul{list-style: none;}
    /* 基础部分*/
    body {
        position: relative;
        height: 100%;
        padding: 0;
        margin: 0 auto;
        font-family: "Microsoft Yahei";
    }

    /*图片列表*/
    .piclist{width: 1100px; margin: 20px 0 0 auto;background-color: #fff;}
    .listul{margin-left: 0;padding-left: 10px; overflow:hidden;height: 450px;}
    .listul li{float: left;width: 260px;height: 230px;position: relative;}
    .listul li span{position: absolute;bottom: 30px;width: 240px;text-align: center;background: rgba(0,0,0,0.6);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000,endColorstr=#99000000); color: #fff;font-size: 15px;padding: 3px 0;}

    /*分页样式*/
    .fydiv{width: 320px;text-align: center;height: 30px;margin: 0 auto;margin-top: 30px;}
    .fenye{text-align: center;height: 30px;margin: 0 auto;}
    .fenye li{float: left;border:1px solid #6CBFB7;height: 26px;line-height: 26px;margin-right: 10px;}
    .fenye li:hover{background-color: #f1f1f1;}
    .fenye li a{text-decoration: none;color:#6CBFB7; display: block; }
    .prev{width: 66px;}
    .next{width: 66px;}
    .numb{width: 26px;}
    .choose{border:1px solid #357973!important;}
    .choose a{color: #357973!important;}
</style>
<div class="pageContent" >

    <div class="piclist">
        <ul class="listul">

        </ul>
        <div class="fydiv">
            <ul class="fenye">

            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    var g_data = ${result};
    getPage(1);
    //翻页
    function getPage(pn){
        var dataCount=${count};//总数据条数

        var pageSize=8;//每页显示条数
        var pageCount= Math.ceil(dataCount / pageSize);//总页数
        if(pn==0||pn>pageCount){
            return;
        }
        var ul=$(".listul");
        ul.empty();
        //console.log(pageCount+"..."+pn)
        paintPage(pageCount,pn);   //绘制页码
        var startPage = pageSize * (pn - 1);

        if (pageCount == 1) {     // 当只有一页时
            for (var j = 0; j < dataCount; j++) {
                var e='<li><a href="'+g_data.stuAns[j].answer+'" target="_blank"><img src="'+g_data.stuAns[j].answer+'" style="width: 240px;height: 200px"></a><p><span>学生'+j+'</span></p></li>';
                ul.append(e);
            }
        }else {      // 当超过一页时
            var e="";
            var endPage = pn<pageCount?pageSize * pn:dataCount;
            for (var j = startPage; j < endPage; j++) {
                var e='<li><a href="'+g_data.stuAns[j].answer+'" target="_blank"><img src="'+g_data.stuAns[j].answer+'" style="width: 240px;height: 200px"></a><p><span>学生'+j+'</span></p></li>';
                ul.append(e);
            }
        }
    }

    //绘制页码
    function paintPage(number,currNum)  //number 总页数,currNum 当前页
    {
        var pageUl=$(".fenye");
        pageUl.empty();
        var ulDetail="";

        if(number==1){
            ulDetail= "<li class=\"prev\"><a href=\"javascript:void(0)\">上一页</a></li>"+
                    "<li class=\"numb choose\"><a href=\"javascript:getPage(1)\">1</a></li>"+
                    "<li class=\"next\"><a href=\"javascript:void(0)\">下一页</a></li>";
        }else if(number==2){
            ulDetail= "<li class=\"prev\"><a href=\"javascript:getPage(1)\">上一页</a></li>"+
                    "<li class=\"numb"+choosele(currNum,1)+"\"><a href=\"javascript:getPage(1)\">1</a></li>"+
                    "<li class=\"numb"+choosele(currNum,2)+"\"><a href=\"javascript:getPage(2)\">2</a></li>"+
                    "<li class=\"next\"><a href=\"javascript:getPage(2)\">下一页</a></li>";
        }else if(number==3){
            ulDetail= "<li class=\"prev\"><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">上一页</a></li>"+
                    "<li class=\"numb"+choosele(currNum,1)+"\"><a href=\"javascript:getPage(1)\">1</a></li>"+
                    "<li class=\"numb"+choosele(currNum,2)+"\"><a href=\"javascript:getPage(2)\">2</a></li>"+
                    "<li class=\"numb"+choosele(currNum,3)+"\"><a href=\"javascript:getPage(3)\">3</a></li>"+
                    "<li class=\"next\"><a href=\"javascript:getPage("+parseInt(currNum+1)+")\">下一页</a></li>";
        }else if(number==currNum&&currNum>3){
            ulDetail= "<li class=\"prev\"><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">上一页</a></li>"+
                    "<li class=\"numb\"><a href=\"javascript:getPage("+parseInt(currNum-2)+")\">"+parseInt(currNum-2)+"</a></li>"+
                    "<li class=\"numb\"><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">"+parseInt(currNum-1)+"</a></li>"+
                    "<li class=\"numb choose\"><a href=\"javascript:getPage("+currNum+")\">"+currNum+"</a></li>"+
                    "<li class=\"next\"><a href=\"javascript:getPage("+currNum+")\">下一页</a></li>";
        }else if(currNum==1&&number>3){
            ulDetail= "<li class=\"prev\"><a href=\"javascript:void(0)\">上一页</a></li>"+
                    "<li class=\"numb choose\"><a href=\"javascript:void(0)\">1</a></li>"+
                    "<li class=\"numb\"><a href=\"javascript:getPage(2)\">2</a></li>"+
                    "<li class=\"numb\"><a href=\"javascript:getPage(3)\">3</a></li>"+
                    "<li class=\"next\"><a href=\"javascript:getPage(2)\">下一页</a></li>";
        }else{
            ulDetail= "<li class=\"prev\"><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">上一页</a></li>"+
                    "<li class=\"numb\"><a href=\"javascript:getPage("+parseInt(currNum-1)+")\">"+parseInt(currNum-1)+"</a></li>"+
                    "<li class=\"numb choose\"><a href=\"javascript:getPage("+currNum+")\">"+currNum+"</a></li>"+
                    "<li class=\"numb\"><a href=\"javascript:getPage("+parseInt(currNum+1)+")\">"+parseInt(currNum+1)+"</a></li>"+
                    "<li class=\"next\"><a href=\"javascript:getPage("+parseInt(currNum+1)+")\">下一页</a></li>";
        }

        $(".fenye").append(ulDetail);

    }

    function choosele(num,cur){
        if(num==cur){
            return " choose";
        }else{
            return "";
        }
    }
</script>