<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="utf-8">
    <title>学科资源信息平台</title>
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="robots" content="" />
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
    <link rel="stylesheet" href="${c_static}/dwz/css/style.css" media="all" />
    <!--[if IE]><link rel="stylesheet" href="css/ie.css" media="all" /><![endif]-->
</head>
<body class="login">
<section>
    <h1><strong>教师专区</strong></h1>
    <form method="link" action="${ctx}/index">
        <input type="text" value="admin" />
        <input type="password" value="123456"/>
        <input type="hidden" name="role" value="1"/>
        <button class="blue">Login</button>
        <#--<a href="${ctx}/index"><button class="blue">Login</button></a>-->
    </form>
    <#--<p><a href="#">Forgot your password?</a></p>-->
</section>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
<script type="text/javascript">
    // Page load delay by Curtis Henson - http://curtishenson.com/articles/quick-tip-delay-page-loading-with-jquery/
    $(function(){
        $('.login button').click(function(e){
            // Get the url of the link
            var toLoad = $(this).attr('href');

            // Do some stuff
            $(this).addClass("loading");

            // Stop doing stuff
            // Wait 700ms before loading the url
            setTimeout(function(){window.location = toLoad}, 10000);

            // Don't let the link do its natural thing
            e.preventDefault
        });

        $('input').each(function() {

            var default_value = this.value;

            $(this).focus(function(){
                if(this.value == default_value) {
                    this.value = '';
                }
            });

            $(this).blur(function(){
                if(this.value == '') {
                    this.value = default_value;
                }
            });

        });
    });
</script>
</body>
</html>