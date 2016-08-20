/**
 * @author Adapted by jfitzgerald
 * @version 4-6-2016
 * @see JQuery Charts Plugin
 * http://canvasjs.com/jquery-charts/
 */

$(document).ready(function () {
  /**
   * Retrieve residence data using ajax call
   */
  $.ajax({
    url: '/adminReports/chart_data',
  }).done(function (data) {
    if (console && console.log) {
      for (let i = 0; i < data.length; i += 1)
        console.log(data[i]);

      render(data);
    }
  });

  /**
   * Render data in adapted CanvasJS pie chart
   */
  function render(residences)
  {
    let dps = dataArray(residences);

    $('#chartContainer').CanvasJSChart({
      title: {
        text: 'Landlord rent rolls as % income all portfolios',
        fontSize: 24,
      },
      axisY: {
        title: 'Rental income %',
      },
      legend: {
        verticalAlign: 'center',
        horizontalAlign: 'right',
      },
      data: [{
        type: 'pie',
        showInLegend: true,
        toolTipContent: '{label} <br/> {y} %',
        indexLabel: '{y} %',
        dataPoints: dps,
      }, ],
    });
  }

  /**
   * Assembles residence data for CanvasJS pie chart display
   * @see Creating Dynamic Charts:
   * http://canvasjs.com/docs/charts/how-to/creating-dynamic-charts/
   *
   * @param Array list of residence objects - see Residence model class.
   * @return Array of dataPoints suitably formatted for CanvasJS rendering.
   */
  function dataArray(residences)  {
    let data = [];
    for (let i = 0; i < residences.length; i += 1)  {
      data.push({ label: residences[i][0],
        y: Number(Math.round((residences[i][1]) * 10) / 10),
        legendText: residences[i][0], });
    }

    return data;
  }
});
