<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>leafletDome</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="${base}/leaflet/leaflet.css"/>
</head>
<script src="${base}/leaflet/leaflet-src.js"></script>
<script src="${base}/leaflet/echarts.min.js"></script>
<script src="https://unpkg.com/esri-leaflet/dist/esri-leaflet.js"></script>
<style>
    .chart {
        width: 500px;
        height: 300px;
        background-color: white;
    }

</style>
<body>
<div id="map" style="width: 100%; height: 650px;"></div>
<script>
    var corner1 = L.latLng(30.67804, 104.04761),
            corner2 = L.latLng(30.663312, 104.085037),
            bounds = L.latLngBounds(corner1, corner2);


    var map = L.map('map', {
        zoomControl: true,  //默认为true,false为关闭缩放插件
        closePopupOnClick: false, //如果您不希望在用户单击地图时关闭弹出窗口，请将其设置为false
        zoomSnap: 0.5,
        keyboardPanDelta: 80
        , minZoom: 15 //最小缩放级别  能够缩小多少
        , maxZoom: 30 //最大缩放级别
        , maxBounds: bounds //设置边界
//        , renderer: L.SVG 或L.Canvas   //在地图上绘制矢量图层的默认方法
//        ,zoomAnimation:false // 缩放动画效果
    }).setView([30.669946, 104.066505], 16);

    //**************************加载瓦片**************************
    //    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    //        maxZoom: 18,
    //        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
    //        '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
    //        'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    //        id: 'mapbox.streets'
    //    }).addTo(map);

    var pp = L.popup();
    //**************************MAP事件**************************
    map.on("contextmenu ", function (e) {
        pp.setLatLng(e.latlng)
                .setContent(e.latlng.toString()).openOn(map);
    });

    //**************************添加标记点并且绑定弹出框**************************
    var marker = L.marker([30.669946, 104.066505], {
        draggable: true, // 使图标可拖拽
        title: 'Text', // 添加一个标题
        opacity: 0.9 // 设置透明度
    }).addTo(map)
            .bindPopup("<b>中国</b><br>冠城广场.")  //绑定点击显示类容
            .openPopup();                          //地图加载完成就显示

    //**************************坐标拖动事件**************************
    marker.on('dragend', function (e) {
        console.log(e);
        alert('我是Marker，被点了。');
    });

    ////**************************地图点击事件**************************
    var mapClick = function (e) {
        //获得点击位置
        L.popup().setLatLng(e.latlng)
                .setContent("你点击的坐标是 " + /*control+*/ e.latlng.toString())
                .openOn(map);
//        var view = this.setView;
//        alert(view);

        //关闭先前打开时打开指定的弹出窗口（以确保一次只能打开一个以获得可用性）。
        /*   var popup = L.popup()
                   .setLatLng(L.latLng(30.667262, 104.066107))
                   .setContent('<p>Hello world!<br />This is a nice popup.</p>')
                   .openOn(map);
           map.openPopup(popup);*/

    };
    map.on("click", mapClick);


    //    var control = new L.Control();
    //    control.setPosition("topright");
    //    control.addTo(map);
    //**************************控件-放大缩小**************************
    L.control.zoom({zoomInTitle: '放大', zoomOutTitle: '缩小'}).addTo(map);
    //**************************控件-说明(没用)**************************
    L.control.attribution({position: 'bottomright', prefix: '你想要的内容'}).addTo(map);
    //**************************控件-尺子**************************
    L.control.scale({maxWidth: 200, metric: true, imperial: false}).addTo(map);
    //**************************控件-选择图层**************************
    mbUrl = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw';
    var mbAttr = 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>';
    //创建图层
    var grayscale = L.tileLayer(mbUrl, {id: 'mapbox.light', attribution: mbAttr, opacity: 0.5}).addTo(map);
    var streets = L.tileLayer(mbUrl, {id: 'mapbox.streets', attribution: mbAttr, opacity: 0.5}).addTo(map);
    //把图层添加进一个对象
    var baseLayers = {
        "图层一": grayscale,
        "图层二": streets
    };
    //标记点
    var overlays = {
        "Marker": L.marker([30.672799, 104.078857]).bindPopup('1'),
        "Roads": L.marker([30.669255, 104.079543]).bindPopup('2')
    };
    L.control.layers(baseLayers, overlays).addTo(map);
    //禁止移动和放大缩小

    /*  map.dragging.disable(); //决定地图是否可被鼠标或触摸拖动.

      map.touchZoom.disable(); //决定地图是否可被两只手指触摸拖拽缩放.

      map.doubleClickZoom.disable(); //决定地图是否可被双击缩放.

      map.scrollWheelZoom.disable(); //决定地图是否被被鼠标滚轮滚动缩放.*/

    //创建控件
    //    var zoomControl = L.control.zoom({
    //        position: 'bottomright'
    //    });
    //    map.addControl(zoomControl);
    //    L.control.scale().addTo(map);

    //    var extend = L.Control.extend({
    //        options: {
    //            position: 'bottomleft' //初始位置
    //
    //        },
    //        initialize: function (options) {
    //            L.Util.extend(this.options, options);
    //
    //        },
    //        onAdd: function (map) {
    //            //创建一个class为leaflet-control-clegend的div
    //            this._container = L.DomUtil.create('div', 'leaflet-control-clegend');
    //            //创建一个图片要素
    //            var legendimg = document.createElement('img');
    //            legendimg.id = 'leaflet-control-clegend';
    //            legendimg.type = 'img';
    //            legendimg.src = "./png/leaf-green.png";
    //            this._legendimg = legendimg;
    //            //创建一个关闭控件的按钮
    //            var closebutton = document.createElement('a');
    //            closebutton.id = 'leaflet-control-geosearch-close';
    //            closebutton.className = 'glyphicon glyphicon-remove';
    //            this._closebutton = closebutton;
    //
    //            this._container.appendChild(this._closebutton);
    //            this._container.appendChild(this._legendimg);
    //            //注册关闭事件
    //            L.DomEvent.addListener(this._closebutton, 'click', this._onCloseControl, this);
    //
    //            return this._container;
    //        },
    //        _onCloseControl: function () {
    //            this._map.options.Legend = false;
    //            this.removeFrom(this._map);
    //
    //        }
    //    });
    //    var extend2 = new extend();
    //    map.addControl(extend2);

    //*******************************定义marker*********************************
    var marker = L.marker([30.671655, 104.059114]).addTo(map);
    var content = '<div style="width: 300px; height: 300px;" id="marker"></div>';
    marker.bindPopup(content, {});
    marker.on('popupopen', function (e) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('marker'));
        // 指定图表的配置项和数据
        option = {
            tooltip: {
                trigger: 'axis'
            },
            xAxis: [{
                type: 'category',
                data: ['1月', '2月', '3月', '4月']
            }],
            yAxis: [{
                type: 'value',
                name: '水量',
                min: 0,
                max: 50,
                interval: 50,
                axisLabel: {
                    formatter: '{value} ml'
                }
            }, {
                type: 'value',
                name: '温度',
                min: 0,
                max: 10,
                interval: 5,
                axisLabel: {
                    formatter: '{value} °C'
                }
            }],
            series: [{
                name: '蒸发量',
                type: 'bar',
                data: [2.0, 4.9, 7.0, 23.2]
            }, {
                name: '降水量',
                type: 'bar',
                data: [2.6, 5.9, 9.0, 26.4]
            }, {
                name: '平均温度',
                type: 'line',
                yAxisIndex: 1,
                data: [2.0, 2.2, 3.3, 4.5]
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    });

    //***********************************echat以控件形式添加在map中****************************
    var chart = L.control({position: 'bottomright'});
    chart.onAdd = function (map) {
        var div = L.DomUtil.create('div', 'info chart');
        div.id = "chatrdemo";
        return div;
    };
    chart.addTo(map);
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('chatrdemo'));
    // 指定图表的配置项和数据
    option = {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [{
            type: 'category',
            data: ['1月', '2月', '3月', '4月']
        }],
        yAxis: [{
            type: 'value',
            name: '水量',
            min: 0,
            max: 50,
            interval: 50,
            axisLabel: {
                formatter: '{value} ml'
            }
        }, {
            type: 'value',
            name: '温度',
            min: 0,
            max: 10,
            interval: 5,
            axisLabel: {
                formatter: '{value} °C'
            }
        }],
        series: [{
            name: '蒸发量',
            type: 'bar',
            data: [2.0, 4.9, 7.0, 23.2]
        }, {
            name: '降水量',
            type: 'bar',
            data: [2.6, 5.9, 9.0, 26.4]
        }, {
            name: '平均温度',
            type: 'line',
            yAxisIndex: 1,
            data: [2.0, 2.2, 3.3, 4.5]
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    //**************************以marker形式添加在map**************************
    var pictures = L.marker([30.664087, 104.057525], {
        icon: L.divIcon({
            className: 'leaflet-echart-icon',
            iconSize: [160, 160],
            html: '<div id="marker1" style="width: 160px; height: 160px; position: relative; background-color: transparent;">asd</div>'
        })
    }).addTo(map);
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('marker1'));
    // 指定图表的配置项和数据
    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [{
            name: '访问来源',
            type: 'pie',
            radius: ['20', '50'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '18',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [{
                value: 1,
                name: '直接访问'
            }, {
                value: 2,
                name: '邮件营销'
            }, {
                value: 3,
                name: '联盟广告'
            }, {
                value: 4,
                name: '视频广告'
            }, {
                value: 20,
                name: '搜索引擎'
            }]
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    //**************************定位**************************
    map.locate({setView: true, maxZoom: 16});
    //**************************Map属性**************************
    map.doubleClickZoom.disable();  //双击缩放 enable()启用disable()禁用enabled()相反
    // **************************地理位置方法Map在取options参数**************************
    var aa = function (e) {
        var view = this.options.maxZoom;
        var paddingTopLeft = map.options.paddingTopLeft = L.point(200, 300);
        console.log(paddingTopLeft);
    };
    map.on("click", aa);

    //////////////////////////////////////////////////////////
    //////////////////////标记Marker//////////////////////////
    //////////////////////////////////////////////////////////

    var addTo = L.marker([30.670031, 104.062973], {
        title: "aaaaaaaaaaaaa" //鼠标放上去的文字
        , alt: "bbbbbbbbbbb"
        , opacity: 0.5
        , riseOnHover: true
        , draggable: true
    }).addTo(map);

    var fff = function (e) {
        console.log("我被移动了");
    };

    var ddd = function (e) {
        console.log("我被关闭了");
    };

    addTo.on("dragend ", fff);
    addTo.on("popupclose  ", ddd());
    // **************************弹出层popup**************************
    var popup = L.popup({
        maxWidth: 1000
        , minWidth: 1000
        , closeButton: false
    }).setLatLng(addTo.getLatLng()).setContent('<p>Hello world!<br />This is a nice popup.</p>');
    var popuptest = function (e) {
//        L.popup({
//            maxWidth: 500
//            , minWidth: 100
//        }, this);
        //弹出层popup
        popup.openOn(map);
        //提示框----------------------
        addTo.bindTooltip("我是提示tooltip text").openTooltip();
    };
    addTo.on("click", popuptest);
    popup.on("add", function () {
        console.log("出来时间");
    });

    //**************************提示层tooltip **************************
    //绑定                   //加载就打开
    // *****addTo.bindTooltip("我是提示tooltip text").openTooltip();*****

    var addTo2 = L.marker([30.672467, 104.067437]).addTo(map);
    addTo2.on("click", function () {
        grayscale.setUrl("https://api.tiles.mapbox.com/v4/mapbox.streets/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw", true);
        console.log("加载了");
    })

    var nexrad = L.tileLayer.wms("http://mesonet.agron.iastate.edu/cgi-bin/wms/nexrad/n0r.cgi", {
        layers: 'nexrad-n0r-900913',
        format: 'image/png',
        transparent: true,
        attribution: "Weather data © 2012 IEM Nexrad"
    });
</script>
</body>
</html>