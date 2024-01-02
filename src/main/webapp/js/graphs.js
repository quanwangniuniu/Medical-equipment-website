// 基于准备好的dom，初始化echarts实例
var myChart1 = echarts.init(document.getElementById('avenue'));

window.onresize = function () {
    myChart.resize();
};


data =
{
    "data": [1, 2, 3, 4, 5, 6, 7, 8],
    "zhi": [12, 13, 14, 20, 16, 10, 9, 17]
}

// 指定图表的配置项和数据
var option = {
    title: {
        text: '销售额'
    },
    tooltip: {},
    legend: {
        data: ['test']
    },
    xAxis: {
        type: 'category',        //坐标轴类型：类目轴
        boundaryGap: false,      //1.基础面积图  ：类目轴中：留白区域，与左边坐标轴和右边图的边缘之间是否有留白。默认true
        data: data.data
    },
    yAxis: {
        type: 'value'        //坐标轴类型：数值轴
    },
    series: [{
        name: '销售额',
        type: 'line',
        data: data.zhi,
        smooth: true,        //平滑曲线图。值可为数字
        areaStyle: {         //2.基础面积图。区域填充样式
            color: '#ccc'    //支持RGB、ALPHA通道+RGBA、十六进制、渐变色、纹理填充
        }
    }]
};

// 使用刚指定的配置项和数据显示图表。
myChart1.setOption(option);



var myChart2 = echarts.init(document.getElementById('rate'));
var option2 = {
    title: {
        text: '销售量'
    },
    xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
                color: 'rgba(180, 180, 180, 0.2)'
            }
        }
    ]
};

// 使用刚指定的配置项和数据显示图表。
myChart2.setOption(option2);

