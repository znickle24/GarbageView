import React from 'react';
import {Line} from 'react-chartjs-2';
import 'chartjs-plugin-annotation';

class LineChartTimes extends React.Component {
  constructor(props) {
    super(props);
    console.log('props state: ', this.props);
    console.log('props: ', this.props.times);
  }

  render(){
    var labelsArr = []
    for(var i = 0; i < this.props.times.length; i++){
      labelsArr.push(i)
    }

    const options = {
        annotation: {
          annotations: [{
            drawTime: 'afterDatasetsDraw',
            // borderColor: 'red',
            // borderDash: [2, 2],
            // borderWidth: 2,
            // mode: 'vertical',
            // type: 'line',
            // value: 10,
            // scaleID: 'x-axis-0',
          }]
        },
      // maintainAspectRation: false
    };

    const data = {
      labels: labelsArr,
      datasets: [
        {
          label: 'Garbage Collector Time per Event',
          fill: false,
          lineTension: 0.1,
          backgroundColor: 'rgba(75,192,192,0.4)',
          borderColor: 'rgba(75,192,192,1)',
          borderCapStyle: 'butt',
          borderDash: [],
          borderDashOffset: 0.0,
          borderJoinStyle: 'miter',
          pointBorderColor: 'rgba(75,192,192,1)',
          pointBackgroundColor: '#fff',
          pointBorderWidth: 1,
          pointHoverRadius: 5,
          pointHoverBackgroundColor: 'rgba(75,192,192,1)',
          pointHoverBorderColor: 'rgba(220,220,220,1)',
          pointHoverBorderWidth: 2,
          pointRadius: 1,
          pointHitRadius: 10,
          // data: [65, 59, 80, 81, 56, 55, 40]
          data: this.props.times
        }
      ]
    };

    return(
      <Line
      data = {data}
      width={15}
      height={5}
      options={options}
      />
    )
  }
}

export default LineChartTimes;