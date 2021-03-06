var pointChart, initPointOpts;
var COLORS = {
    "monitor_accessible": "hsl(128, 99%, 65%)",
    "monitor_image_available": "hsl(128, 99%, 43%)",
    "monitor_controllable": "hsl(129, 100%, 31%)",
    "monitor_implantable": "hsl(128, 100%, 17%)",
    "industry_control_accessible": "hsla(5, 73%, 74%, 1)",
    "industry_control_image_available": "hsla(6, 79%, 63%, 1)",
    "industry_control_controllable": "hsla(6, 73%, 53%, 1)",
    "industry_control_implantable": "hsla(6, 100%, 43%, 1)",
    "security_matter_accessible": "hsl(273, 97%, 87%)",
    "security_matter_image_available": "hsl(271, 100%, 73%)",
    "security_matter_controllable": "hsl(272, 100%, 60%)",
    "security_matter_implantable": "hsl(272, 99%, 47%)",
    "network_device_accessible": "hsl(0, 0%, 68%)",
    "network_device_image_available": "hsl(0, 0%, 48%)",
    "network_device_controllable": "hsl(0, 0%, 31%)",
    "network_device_implantable": "hsl(0, 0%, 0%)",
    "brand1": "#ff69b4",
    "brand2": "orange",
    "brand3": "cyan"
};

function beforeInit() {
    //console.log("beforeInit      starts");
    var width = $(window).width(), height = $(window).height();
    $('#markpointHolder').css({
        "width": width,
        "height": height
        //"margin-bottom": "5rem"
    });
}
function starts() {
    beforeInit();

    require.config({
        paths: {
            'echarts': basePath2 + "resources/plugins/echarts-2.2.7/build/dist",
            'echarts-x': basePath2 + "resources/plugins/echarts-x/build/dist"
        }
    });

    require([
        'echarts/echarts',
        'echarts/chart/map',
        'echarts-x/echarts-x',
        'echarts-x/chart/map3d'
    ], function (ec) {
        //初始化地球和charts
        //beforeInit();
        pointChart = ec.init(document.getElementById('markpointHolder'));
        initPointOpts = {
            tooltip: {
                formatter: '{b}'
            },
            series: [
                {
                    type: 'map3d',
                    mapType: 'world',
                    selectedMode: 'single',
                    baseLayer: {
                        backgroundColor: '',
                        backgroundImage: basePath2 + 'resources/img/earth.jpg',
                        quality: 'high'
                    },
                    light: {
                        show: true,
                        sunIntensity: 1,
                        ambientIntensity: 0.4
                    },
                    itemStyle: {
                        normal: {
                            borderWidth: 1,
                            borderColor: 'yellow',
                            areaStyle: {
                                color: 'rgba(0, 0, 0, 0)'
                            }
                        }
                    },
                    autoRotate: true
                }, {//添加这个数据是为了不出现loading画面，因为必须有数据才可以正常显示，这里只是一个虚拟的点
                    type: 'map3d',
                    name: "nowhere",
                    markPoint: {
                        effect: {
                            show: false //不显示这个点
                        },
                        data: [
                            {
                                geoCoord: []
                            }
                        ]
                    }
                }
            ]
        };
        pointChart.setOption(initPointOpts);
        pointChart.hideLoading();
    });

    /*
     * chart: echarts实例
     * url：ajax获取数据的地址
     */
    function SetChart(url, chart) {
        //console.log(chart);
        $.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            timeout: 100000
        })
            .success(function (data) {
                //console.log(data.data);
                if (data.statuscode == 200) {
                    /*for multi type of device*/
                    $.each(data.data, function (key, value) {
                        if (value.length > 0) {
                            //console.log("value", value, 'key=' + key);
                            for (var i = 0; i < value.length; i++) {
                                if (!value[i].geoCoord) {
                                    console.log(i, value[i].geoCoord);
                                    value.splice(i, 1);
                                }
                            }
                            var markPointColor = COLORS[key] == undefined ? "yellow" : COLORS[key],
                                legendData = [],
                                series = [
                                    {
                                        type: 'map3d',
                                        mapType: 'world',
                                        selectedMode: 'single',
                                        baseLayer: {
                                            backgroundColor: '',
                                            backgroundImage: basePath2 + 'resources/img/earth.jpg',
                                            quality: 'high'
                                        },
                                        light: {
                                            show: true,
                                            sunIntensity: 1,
                                            ambientIntensity: 0.4
                                        },
                                        itemStyle: {
                                            normal: {
                                                borderWidth: 1,
                                                borderColor: 'yellow',
                                                areaStyle: {
                                                    color: 'rgba(0, 0, 0, 0)'
                                                }
                                            }
                                        },
                                        autoRotate: true
                                    }],
                                markPointStyle = {
                                    normal: {
                                        color: markPointColor
                                    }
                                },
                                markPointData = value.map(function (item) {
                                    return {
                                        itemStyle: markPointStyle,
                                        geoCoord: item.geoCoord     //经纬度
                                    }
                                });
                            legendData.push(key);
                            series.push(
                                {
                                    type: 'map3d',
                                    name: key,
                                    markPoint: {
                                        effect: {
                                            show: true
                                        },
                                        large: true,
                                        symbolSize: 5,
                                        data: markPointData
                                    }
                                }
                            );
                            var opts = {
                                /* title: {
                                 text: '3D地图 ',
                                 subtext: '设备 ',
                                 x: 'right',
                                 y: 'top',
                                 textStyle: {
                                 color: 'white'
                                 }
                                 },*/
                                tooltip: {
                                    formatter: '{b}'
                                },
                                /*legend: {
                                 show: false,
                                 data: legendData,
                                 selected: {},
                                 x: 'left',
                                 orient: 'vertical',
                                 textStyle: {
                                 color: 'white'
                                 }
                                 },*/
                                series: series
                            };
                            //console.log(opts);
                            chart.setOption(opts, true);
                            chart.hideLoading();
                        } else {
                            console.log("暂无该类型设备！");
                            chart.setOption(initPointOpts);
                        }
                    });
                } else {
                    console.log(data.statuscode + ": " + data.errmsg);
                    chart.setOption(initPointOpts, true);
                    alert("暂无数据!");
                }
            })
            .error(function (e) {
                console.log("AJAX Error", e);
            })
            .fail(function (f) {
                console.log("AJAX Failed!", f);
            })
            .done(function (d) {
                //console.log("AJAX Done", d);
            });
    }

    //监听点击事件
    $('a').on('click', function (e) {
        e.preventDefault();
        var url = this.href;
        if (url != undefined) {
            SetChart(url, pointChart);
        }
    });
}

/**
 *删除数组指定下标或指定对象
 */
Array.prototype.remove = function (obj) {
    for (var i = 0; i < this.length; i++) {
        var temp = this[i];
        if (!isNaN(obj)) {
            temp = i;
        }
        if (temp == obj) {
            for (var j = i; j < this.length; j++) {
                this[j] = this[j + 1];
            }
            this.length = this.length - 1;
        }
    }
};