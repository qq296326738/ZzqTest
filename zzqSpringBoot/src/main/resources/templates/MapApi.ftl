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

</style>
<body>
<div id="map" style="width: 100%; height: 550px;"></div>
<div style="width: 100%; background-color: #0078A8;height:30px">
    <input type="button" value="按钮1" style="width: 19.7%"/>
    <input type="button" value="按钮2" style="width: 19.7%"/>
    <input type="button" value="按钮3" style="width: 19.7%"/>
    <input type="button" value="按钮4" style="width: 19.7%"/>
    <input type="button" value="按钮5" style="width: 19.7%"/>
</div>
<div style="width: 100%; background-color: #0078A8;">
    <input type="button" value="按钮6" style="width: 19.7%;height: 20px;background-color: chartreuse"/>
    <input type="button" value="按钮7" style="width: 19.7%;height: 20px;background-color: chartreuse"/>
    <input type="button" value="按钮8" style="width: 19.7%;height: 20px;background-color: chartreuse"/>
    <input type="button" value="按钮9" style="width: 19.7%;height: 20px;background-color: chartreuse"/>
    <input type="button" value="按钮10" style="width: 19.7%;height: 20px;background-color: chartreuse"/>
</div>
<script>
    /***********************************************MAP创建选项************************************************************/
    var map = L.map("map", {
        center: [50.5, 30.5], //初始化地图的中心点位置
        zoom: 15         //初始化地图的缩放等级
        , attributionControl: false //默认情况下，是否将 attribution版权控件添加到地图中.
        , zoomControl: true //默认情况下，是否将 zoom缩放控制 添加到地图中
        , closePopupOnClick: false //如果你不想让Popup弹窗在用户点击地图时关闭，那就把它设为false
        , zoomSnap: 1 //可以设置小于1（例如0.5）的 值允许更大的展示粒度
        , boxZoom: true //按住Shift键的同时拖动鼠标，地图是否可以缩放到指定的矩形区域。
        , doubleClickZoom: true //地图是否可以通过双击放大，并通过双击同时按住shift缩小。如果设置为 'center'，双击缩放将缩放到视图的中心，而不管鼠标在哪里。
        , dragging: true //地图是否可以通过鼠标/触摸拖动。
        /*,crs:L.CRS.EPSG3857*/ //该地图的坐标系。如果你不确定坐标系这是什么意思，请不要改变它
        , minZoom: undefined //地图最小缩放等级，地图不显示小于minZoom的级别.
        , maxZoom: undefined //地图最大缩放等级，地图不显示大于maxZoom的级别.
        , maxBounds: null //当这个选项被设置后，地图被限制在给定的地理边界内,. 要动态设置此限制，请使用setMaxBounds方法。
        , worldCopyJump: false //启用此选项后，地图将跟踪当您平移到另一个“复制”的世界地图时，一些如标记和矢量图层等所有叠加元素仍然同步可见。
        , maxBoundsViscosity: 0.0// 如果maxBounds设置，该选项将控制当拖动地图时边界的固定度。默认值0.0允许用户以正常速度拖动界限，较高的值将减慢地图拖动,如设置值1.0将使边界完全固定，防止用户拖动界限。
        , bounceAtZoomLimits: true //如果您不希望在地图缩放超过最小/最大缩放范围时反弹，请将其设置为false。
    });

    map.on('click', function (e) {
//        L.popup().setLatLng(e.latlng).setContent(e.latlng.toString()).openOn(map);
        console.log(e.latlng.lat + "," + e.latlng.lng);
    });
    /***********************************************Marker 点标记************************************************************/
    var marker = L.marker([50.5, 30.5], {
//        icon: * //自定义标记图标的 参数类型 Icon
        draggable: false //标记是否可以用鼠标/触摸拖动。
        , keyboard: true //标记是否可以用键盘按键并按回车键。
        , title: '标记'
        , opacity: 0.9 // 透明度
        , riseOnHover: false //如果为true，当您将鼠标悬停在其上时，标记将会放在其他顶部。
        , riseOffset: 250  //用于riseOnHover功能的z-index偏移量。
        , pane: "markerPane" //标记图标将被添加的地图pane窗格
    }).addTo(map);
    /***********************************************Popup弹窗************************************************************/
    //用于在地图的某些地方打开弹出窗口。
    // 使用Map.openPopup打开弹出窗口，
    // 同时确保一次只打开一个弹出窗口（推荐用于可用性），
    // 或使用map.addLayer 打开所需的多个弹出窗口。
    marker.bindPopup("加载就打开").openPopup();

    var popup = L.popup({
        maxWidth: 300  //弹窗的最大宽度，单位为像素
        , minWidth: 50 //弹窗的最小宽度，单位为像素
        , maxHeight: null //如果设置，如果内容超过此高度时，则在弹出窗口中显示滚动条。
        , autoPan: true // 如果您不希望地图进行平移动画以适应打开的弹出窗口， 请将其设置为false。
        , keepInView: false //如果你想防止用户在屏幕打开时弹出屏幕上的弹出窗口，将其设置为true.
        , closeButton: true //弹窗中是否存在关闭按钮
        , closeOnClick: false //如果要覆盖用户在地图上单击的弹出窗口关闭的默认行为，请设置它。默认为Map的closePopupOnClick选项
        , autoClose: true //如果在打开另一个弹窗时，是否自动关闭之前的弹窗.
        , className: '' //要分配给弹窗的自定义的css类名
    })
            .setLatLng(L.latLng([50.5, 30.5]))
            .setContent('<p>Hello world!<br />我是弹出框 popup.</p>')
            .openOn(map);

    /***********************************************Tooltip 鼠标悬停提示框************************************************************/
    marker.bindTooltip("鼠标悬停提示框").openTooltip()
    /*L.tooltip(<Tooltip options> options?, <Layer> source?)*/
    /***********************************************TileLayer瓦片图层************************************************************/
    L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png?{foo}', {
        foo: 'bar',
        minZoom: 0 //最小的缩放级别
        , maxZoom: 18 //最大的缩放级别
        , errorTileUrl: ""  //显示加载瓦片失败时，显示的图片的url
        , updateWhenIdle: "depends" //如果false在平移期间加载新的瓦片，否则只能在其后（为了更好的性能）。默认情况下true，在移动浏览器上false。

    }).addTo(map);
    /************TileLayer.WMS  用于加载显示OGC标准的 WMS地图服务*************/
    var nexrad = L.tileLayer.wms("http://mesonet.agron.iastate.edu/cgi-bin/wms/nexrad/n0r.cgi", {
        layers: 'nexrad-n0r-900913',
        format: 'image/png',
        transparent: true,
        attribution: "Weather data © 2012 IEM Nexrad"
    });
    /************ImageOverlay 加载和显示单张图像*************/

    var imageUrl = 'http://www.lib.utexas.edu/maps/historical/newark_nj_1922.jpg',
            imageBounds = [[50.509933, 30.536156], [50.493337, 30.576324]];
    L.imageOverlay(imageUrl, imageBounds, {
        opacity: 0.5  //透明度
        , interactive: true //如果true，当点击或悬停时，图像叠加层将发出鼠标事件
    }).addTo(map);
    /************Polyline 折线*************/
            // 根据数组参数点，创建红色折线
    var latlngs = [
                [50.499929813619715, 30.48342347145081],
                [50.49981379886273, 30.487532615661625],
                [50.5007896788175, 30.491330623626713]
            ];

    //也可以传递一个坐标数组
    var polyline = L.polyline(latlngs, {
        color: 'red'
        , smoothFactor: 20 //数值的大小可以简化每个缩放级别的折线。更多的意味着更好的性能和更平滑的外观，而更少的意味着更准确的表示。
        , noClip: true       //禁用折线裁剪.
        , weight: 3     // 线条宽度
    }).addTo(map);
    // 缩放地图到折线所在区域
    //    map.fitBounds(polyline.getBounds());
    /************Polygon 多边形*************/
    var latlngs = [
        [[50.50021302491773, 30.492950677871708]
            , [50.50020278839437, 30.495294928550724]
            , [50.49851373165613, 30.496265888214115]
            , [50.49481124447395, 30.497027635574344]
            , [50.497507093359744, 30.49261808395386]
            , [50.49866046019266, 30.49167394638062]
            , [50.499718257084204, 30.490955114364628]],
        [[50.49833970419634, 30.493294000625614]
            , [50.49827828258686, 30.495085716247562]
            , [50.49798482268358, 30.494924783706665]
            , [50.49798482268358, 30.493712425231937]]
    ];
    var polygon = L.polygon(latlngs, {color: 'red'}).addTo(map);
    /************Rectangle 矩形*************/
    var bounds = [[50.499452103967734, 30.49911975860596], [50.49735012409872, 30.503582954406742]];
    L.rectangle(bounds, {color: "#ff7800", weight: 1}).addTo(map);
    /************Circle 圆*************/
    L.circle([50.4954664522794, 30.50791740417481], {radius: 200}).addTo(map); //单位:米
    L.circleMarker([50.4954664522794, 30.50791740417481], {radius: 100}).addTo(map); //单位:像素
    /************SVG 渲染器*************/
    /*var map = L.map('map', {
        renderer: L.svg()
     });*/

    //    map.options.renderer = L.svg();

    //例化的SVG渲染器：
    //    var myRenderer = L.svg({padding: 5.0});
    //填充的Canvas渲染器
    var myRenderer = L.canvas({padding: 0.5});
    var circle = L.circle([50.50068049045375, 30.52139282226563], {
//        radius: 200,
        renderer: myRenderer
    }).addTo(map);

    var pane = circle.getPane();
    console.log(pane);
    /************layerGroup 图层管理*************/
    var m1 = L.marker([50.50149939702891, 30.50787448883057]);
    var m2 = L.marker([50.50128102333053, 30.51036357879639]);
    var c1 = L.circle([50.503928736323594, 30.512552261352543], {radius: 200}); //单位:米
    var addTo = L.layerGroup([m1, m2])
            .addLayer(c1);
    addTo.on('add', function () {
        console.log("图层组添加了");
    });
    addTo.bindPopup("ABC");
    addTo.addTo(map);
    //    //遍历添加..............
    //    addTo.eachLayer(function (e) {
    //        e.bindPopup("hello")
    //    })
    /************FeatureGroup 图层管理*************/
    //扩展继承自LayerGroup，使得它更容易对其所有成员层做同样的事情
    L.featureGroup([m1, m2, c1])
            .bindPopup('Hello world!')
            //            .on('click', function() { alert('Clicked on a member of the group!'); })
            .addTo(map);

    /************GeoJSON *************/
    //表示GeoJSON对象或GeoJSON对象数组。允许您解析GeoJSON数据并将其显示在地图上
    /*使用示例
    L.geoJSON(data, {
        style: function (feature) {
            return {color: feature.properties.color};
        }
    }).bindPopup(function (layer) {
        return layer.feature.properties.description;
    }).addTo(map);*/
    /************GridLayer *************/
    /*用于处理HTML元素的平铺网格的通用类。这是所有tile层和替换的基类TileLayer.Canvas。
     GridLayer可以扩展到创建HTML元素的像平铺格<canvas>, <img> 或 <div>。GridLayer将为您处理创建这些DOM元素。*/


</script>
</body>
</html>