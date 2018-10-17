<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>jquery_table</title>
</head>
<script src="${base}/jquery/jquery-3.2.0.min.js"></script>
<script src="${base}/jquery/jquery.table2excel.js"></script>
<body>
<div class="table2excel">
    <table id="tableExcel" width="100%" border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td colspan="5" align="center">html 表格导出道Excel</td>
        </tr>
        <tr>
            <td rowspan="4">列标题1</td>
            <td>列标题2</td>
            <td>类标题3</td>
            <td>列标题4</td>
            <td>列标题5</td>
        </tr>
        <tr>
            <#--<td>aaa</td>-->
            <td>bbb</td>
            <td colspan="2" rowspan="2">ccc</td>
            <#--<td>ddd</td>-->
            <td>eee</td>
        </tr>
        <tr>
            <#--<td>AAA</td>-->
            <td>BBB</td>
            <#--<td>CCC</td>-->
            <#--<td>DDD</td>-->
            <td>EEE</td>
        </tr>
        <tr>
            <#--<td>FFF</td>-->
            <td>GGG</td>
            <td>HHH</td>
            <td>III</td>
            <td>JJJ</td>
        </tr>
    </table>
</div>
<input class="btn" type="button"  value="点击导出">
<script type="text/javascript">
    $(function () {
        $(".btn").click(function () {
            $(".table2excel").table2excel({
                // 不被导出的表格行的CSS class类
                exclude: ".noExl",
                // 导出的Excel文档的名称
                name: "Excel Document Name",
                // Excel文件的名称
                filename: "test",
                //文件后缀名
                fileext: ".xls",
                //是否排除导出图片
                exclude_img: false,
                //是否排除导出超链接
                exclude_links: false,
                //是否排除导出输入框中的内容
                exclude_inputs: false
            });
        });
    });
</script>
</body>
</html>