/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function set_time_zone(server_date)
{
    var date = new Date();
    var form = $("<form></form>");
    form.append("<input type='hidden' name='diff' value='"+Math.round((date-new Date(server_date))/(1000*60*60))+"'/>");
    form.attr("method","POST");
    form.attr("action","editTimeZoneDiff.html");
    form.submit();
    return false;
}
  