/*
 * Created by lyp on 2016/2/26.
 * Author: lyp
 * Date: 2016/2/26
 * Description: 3维地图上的设备展示
 * Version: V1.0 
 */
var MarkPoint = {
    _COLORS: (function () {
        return {
            'monitor_accessible': 'hsl(128, 99%, 65%)',
            'monitor_image_available': 'hsl(128, 99%, 43%)',
            'monitor_controllable': 'hsl(129, 100%, 31%)',
            'monitor_implantable': 'hsl(128, 100%, 17%)',
            'industry_control_accessible': 'hsla(5, 73%, 74%, 1)',
            'industry_control_image_available': 'hsla(6, 79%, 63%, 1)',
            'industry_control_controllable': 'hsla(6, 73%, 53%, 1)',
            'industry_control_implantable': 'hsla(6, 100%, 43%, 1)',
            'security_matter_accessible': 'hsl(273, 97%, 87%)',
            'security_matter_image_available': 'hsl(271, 100%, 73%)',
            'security_matter_controllable': 'hsl(272, 100%, 60%)',
            'security_matter_implantable': 'hsl(272, 99%, 47%)',
            'network_device_accessible': 'hsl(0, 0%, 68%)',
            'network_device_image_available': 'hsl(0, 0%, 48%)',
            'network_device_controllable': 'hsl(0, 0%, 31%)',
            'network_device_implantable': 'hsl(0, 0%, 0%)',
            'brand1': '#ff69b4',
            'brand2': 'orange',
            'brand3': 'cyan',
            'others': 'yellow'
        }
    }()),
    _HOLDER_SEL: (function () {
        return '#markpointHolder';
    }()),
    _pointChart: {},
    _initPointOpts: {},
    beforeInit: function () {
        var width = $(window).width(), height = $(window).height();
        $(this._HOLDER_SEL).css({
            "width": width,
            "height": height
        });
    },
    init: function () {
        var basePath = getRootPath();
        var pointChart, initPointOpts;
        this.beforeInit();
        require.config({
            paths: {
                'echarts': basePath + '/resources/plugins/echarts-2.2.7/build/dist',
                'echarts-x': basePath + '/resources/plugins/echarts-x/build/dist'
            }
        });
        require([
            'echarts/echarts',
            'echarts/chart/map',
            'echarts-x/echarts-x',
            'echarts-x/chart/map3d'
        ], function (ec) {
            //初始化地球和charts
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
                            backgroundImage: basePath + 'resources/img/earth.jpg',
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
            this._pointChart = pointChart;
            this._initPointOpts = initPointOpts;
        });
    },
    start: function () {
        //监听点击事件
        $('#main').find('a').on('click', function (e) {
            e.preventDefault();
            var url = this.href;
            if (url != undefined) {
                MarkPoint.setChart(url, MarkPoint._pointChart);
            }
        });
    },
    setChart: function (url, chart) {
        $.ajax({
            type: "POST",
            url: url,
            dataType: 'json',
            timeout: 100000
        })
            .success(function (data) {
                if (data.statuscode == 200) {
                    var colors = MarkPoint._COLORS, initPointOpts = MarkPoint._initPointOpts;
                    /*for multi type of device*/
                    $.each(data.data, function (key, value) {
                        if (value.length > 0) {
                            var markPointColor = colors[key] == undefined ? colors['others'] : colors[key],
                                legendData = [],
                                series = [
                                    {
                                        type: 'map3d',
                                        mapType: 'world',
                                        selectedMode: 'single',
                                        baseLayer: {
                                            backgroundColor: '',
                                            backgroundImage: basePath + 'resources/img/earth.jpg',
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
                                };
                            var markPointData = value.map(function (item) {
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
                            console.log(opts);
                            chart.setOption(opts, true);
                            chart.hideLoading();
                        } else {
                            alert("暂无该类型设备！");
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
                console.log("AJAX Done", d);
            });
    },
    onLeave: function () {
    }
};