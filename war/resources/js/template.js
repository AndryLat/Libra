/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function toggleedit(selector)
{
    $(selector).toggle();
    return false;
}
/*
function submitDelete()
{
    var form = document.createElement("form");
    form.setAttribute("method", "POST");
    form.setAttribute("action", "delTemplate.html");
    for (var i in $(':checkbox[name^=template]'))
        form.appendChild(i);
    form.submit();
    return false;
}*/

function cbToggle()
{
    var maincb = $(':checkbox#one');
    var others = $(':checkbox').not('#one').not(':disabled');
    others.prop('checked', maincb.prop('checked'));
    return false;
}

function submitDelete(dAction,dSelector)
{
    var form = $("<form></form>");
    $(dSelector).clone().appendTo(form);
    form.attr("method","POST");
    form.attr("action",dAction);
    form.attr("style","display: hidden;");
    $('body').append(form);
    form.submit();
    /*
    var form = document.createElement("form");
    form.setAttribute("method", "POST");
    form.setAttribute("action", dAction);
    for (var i in $(dSelector))
        if (i!=null)
        form.appendChild(i);
    form.submit();*/
    return false;
}

function number_control()
{
    //Вызывается когда вводятся символы в поле с id quantity
    $("#quantity").keypress(function (e)  
    { 
      //Если символ - не цифра, ввыодится сообщение об ошибке, другие символы не пишутся
      if (e.shiftKey || (e.keyCode < 48 || e.keyCode > 57) && (e.keyCode < 96 || e.keyCode > 105))
      {
        return false;
      }  

    });
  }
    
  function ajax_result(page,count,serch,order,desc)
  {
      u='resultAjax.html';
      param=false;
      $("#serch_info").empty();
      if(order==null&&desc==null)
      {
          $("span[id$=arrow]").empty();
          $("#results_arrow").append("&uarr;");
      }
      if(order!=null)
      {
          //deelete arrow from the table
          $("span[id$=arrow]").empty();
          $("#order").empty();
          //Add order in form
          $("#order").append("<input type=\"hidden\"  name=\"order\" value=\""+order+"\"/>");
          $("#order").append("<input type=\"hidden\"  name=\"desc\" value="+desc+">");
          //Make url
          u=u+'?order='+order;
          if(!desc||desc=="false")
              arrow='&darr;';
          else
              arrow='&uarr;';  
            //Add arrow in the table 
           $("#"+order+"_arrow").append(arrow);
            param=true;
    }
      if(page!=null)
      {
          if(param)
          {
              u=u+'&page='+page;
          }
          else
          {
              u=u+'?page='+page;
          }
          param=true;
      }
      if(count!=null)
      {
          if(param)
          {
              u=u+'&count='+count;
          }
          else
          {
              u=u+'?count='+count;
          }
          param=true;
      }
      if(serch!=null)
      {
          $("#serch_info").append("Результат поиска");
          if(param)
          {
              u=u+'&serch='+serch;
          }
          else
          {
              u=u+'?serch='+serch;
          }
          param=true;
      }
      if(desc!=null)
      {
          if(param)
          {
              u=u+'&desc='+desc;
          }
          else
          {
              u=u+'?desc='+desc;
          }
          param=true;
      }
    $.ajax(
    {
        url: u,             // указываем URL и
        dataType : "json",                     // тип загружаемых данных
        success: function (data, textStatus) 
        { // вешаем свой обработчик на функцию success
            $("#studentTable").empty();
            $.each(data.students, function(i, val) 
            {    // обрабатываем полученные данные
                var tr = $("<tr></tr>");
                $("<td></td>").append("<input type=\"checkbox\" class=\"checkbox\" name=\"emails[]\" value="+val.email+">").appendTo(tr);
                $("<td></td>").append(val.r).appendTo(tr);         
                $("<td></td>").append(val.appId).appendTo(tr);
                $("<td></td>").append(val.fio).appendTo(tr);
                $("<td></td>").append(val.email).appendTo(tr);
                $("<td></td>").append(val.avgMark).appendTo(tr);
                $("<td></td>").append("<a href='addResult.html?appId="+val.appId+"'>Комментарий</a>").appendTo(tr)
                tr.appendTo("#studentTable");
            });
            
            //Make pages
            var pages=data.pages;
            currentpage=data.currentpage;
            count=data.count;
            order=data.order;
            desc=data.desc;
            $("#pages").empty();
           // $("#pages").append(pages)
           if(pages!=null)
           {
               var ul = $("<ul></ul>");
               if(currentpage==1)
                {
                    $("<li class=\"disabled\"><a>Prev</a></li>").appendTo(ul);
                }   
                else
                {
                     $("<li><a href='#' onclick=\"ajax_result("+(currentpage-1)+","+count+",null,'"+order+"',"+desc+")\" >Prev</a></li>").appendTo(ul);           
                }
                if(pages<11)
                {
                    for(var i=1;i<=pages;i++)
                    {
                        if(currentpage==i)
                        {
                         $("<li class=\"active\"><a href='#' onclick=\"ajax_result("+i+","+count+",null,'"+order+"',"+desc+")\" > "+i+"</a></li>").appendTo(ul);
                        }
                        else
                        {
                            $("<li><a href='#' onclick=\"ajax_result("+i+","+count+",null,'"+order+"',"+desc+")\" > "+i+"</a></li>").appendTo(ul);
                        }      
                    }
                     ul.appendTo("#pages");
                }
                else
                {
                    if(currentpage<=6)
                    {
                        for(var i=1;i<=7;i++)
                        {
                            if(currentpage==i)
                            {
                             $("<li class=\"active\"><a href='#' onclick=\"ajax_result("+i+","+count+",null,'"+order+"',"+desc+")\" > "+i+"</a></li>").appendTo(ul);
                            }
                            else
                            {
                                $("<li><a href='#' onclick=\"ajax_result("+i+","+count+",null,'"+order+"',"+desc+")\" > "+i+"</a></li>").appendTo(ul);
                            }  
                        }
                            $("<li><a >...</a></li>").appendTo(ul);
                            $("<li><a href='#' onclick=\"ajax_result("+(pages-2)+","+count+",null,'"+order+"',"+desc+")\" > "+(pages-2)+"</a></li>").appendTo(ul);
                            $("<li><a href='#' onclick=\"ajax_result("+(pages-1)+","+count+",null,'"+order+"',"+desc+")\" > "+(pages-1)+"</a></li>").appendTo(ul);
                            $("<li><a href='#' onclick=\"ajax_result("+(pages)+","+count+",null,'"+order+"',"+desc+")\" > "+(pages)+"</a></li>").appendTo(ul);
                           ul.appendTo("#pages");    
                    }
                    else
                    {
                        if(currentpage>pages-5)
                        {
                               
                            $("<li><a href='#' onclick=\"ajax_result("+1+","+count+",null,'"+order+"',"+desc+")\" > "+1+"</a></li>").appendTo(ul);
                            $("<li><a href='#' onclick=\"ajax_result("+2+","+count+",null,'"+order+"',"+desc+")\" > "+2+"</a></li>").appendTo(ul);
                            $("<li><a href='#' onclick=\"ajax_result("+3+","+count+",null,'"+order+"',"+desc+")\" > "+3+"</a></li>").appendTo(ul);
                            $("<li><a >...</a></li>").appendTo(ul);
                            for(var i=5;i>=0;i--)
                            {
                                if(currentpage==(pages-i))
                                {
                                 $("<li class=\"active\"><a href='#' onclick=\"ajax_result("+(pages-i)+","+count+",null,'"+order+"',"+desc+")\" > "+(pages-i)+"</a></li>").appendTo(ul);
                                }
                                else
                                {
                                    $("<li><a href='#' onclick=\"ajax_result("+(pages-i)+","+count+",null,'"+order+"',"+desc+")\" > "+(pages-i)+"</a></li>").appendTo(ul);
                                }  
                            }
                          ul.appendTo("#pages");    
                        }
                        else
                        {
                            for(var i=1;i<=3;i++)
                            {
                                $("<li><a onclick=\"ajax_result("+i+","+count+",null,'"+order+"',"+desc+")\" > "+i+"</a></li>").appendTo(ul);
                            }
                            $("<li><a >...</a></li>").appendTo(ul);
                            $("<li ><a href='#' onclick=\"ajax_result("+(currentpage-1)+","+count+",null,'"+order+"',"+desc+")\" > "+(currentpage-1)+"</a></li>").appendTo(ul);
                            $("<li class=\"active\"><a href='#' onclick=\"ajax_result("+(currentpage)+","+count+",null,'"+order+"',"+desc+")\" > "+(currentpage)+"</a></li>").appendTo(ul);
                            $("<li ><a href='#' onclick=\"ajax_result("+(currentpage+1)+","+count+",null,'"+order+"',"+desc+")\" > "+(currentpage+1)+"</a></li>").appendTo(ul);
                            $("<li><a >...</a></li>").appendTo(ul);
                            for(var i=2;i>=0;i--)
                            {
                                $("<li><a href='#' onclick=\"ajax_result("+(pages-i)+","+count+",null,'"+order+"',"+desc+")\" > "+(pages-i)+"</a></li>").appendTo(ul);
                            }
                            ul.appendTo("#pages");    
                        }
                    }
                }
                if(currentpage==pages)
                {
                    $("<li class=\"disabled\"><a>Next</a></li>").appendTo(ul);
                }   
                else
                {
                     $("<li><a href='#' onclick=\"ajax_result("+(currentpage+1)+","+count+",null,'"+order+"',"+desc+")\" >Next</a></li>").appendTo(ul);
                
                }
           }
           
            //$("#page").empty();
           // $("#page").append("<span>"+data.page+"</span>");

        },
        error: function ( jqXHR, textStatus, errorThrown )
        {
            console.log(errorThrown);
        }
    }
    );
  }
  
  function format_submit()
  {
      val = $(":text[name=count]");
      order=$(":hidden[name=order]");
       desc=$(":hidden[name=desc]");
      cval = val.val();
      orderval=order.val();
      if(desc.val()=='false')
      {
          descval=false;
      }  
      else
      {
          descval=true;
      }
      var orders=['appId','results','email','lastname'];
      var links = new Array(orders.length);
      for(var i=0;i<orders.length;i++)
      {
          switch (orders[i]) 
            {
              case 'appId':
                links[i]='По номеру анкеты';
                break
              case 'results':
                links[i]='По результатам';
                break
              case 'email':
                links[i]='По email';
                break
              default:
                links[i]='По фамилие';
            }  
            $("#serch_"+orders[i]).empty();
            if(orders[i]==order)
            {
                set_serch_div(orders[i],1,null,!descval,links[i],cval);
            }
            else
            {
                set_serch_div(orders[i],1,null,descval,links[i],cval);
            }
               
       }
      ajax_result(1,cval,null,orderval,descval);
      return false;
  }
  function set_serch_div(order,page,serch,desc,link,count)
  {
    $("#serch_"+order).append("<a onclick='change_order("+page+","+count+","+serch+",\""+order+"\","+desc+")' >"+link+"</a>");   
  }
  function serch_submit()
  {
      $("span[id$=arrow]").empty();
      val = $(":text[name=serch]");
      cval = val.val();
      ajax_result(null,null,cval,null,true);
      return false;
  }
  
  function empty_all()
  {
      $("span[id$=arrow]").empty();
      $("#results_arrow").append('&uarr;');
      var order=['appId','results','email','lastname'];
      var links = new Array(order.length);
      for(var i=0;i<order.length;i++)
      {
          switch (order[i]) 
            {
              case 'appId':
                links[i]='По номеру анкеты';
                break
              case 'results':
                links[i]='По результатам';
                break
              case 'email':
                links[i]='По email';
                break
              default:
                links[i]='По фамилие';
            }  
            $("#serch_"+order[i]).empty();
            if(order[i]!='results')
            {
                set_serch_div(order[i],null,null,true,links[i],null);
            }
            else
            {
                set_serch_div(order[i],null,null,false,links[i],null);
            }
               
       }
       ajax_result(null,null,null,null,null);
  }
  
  function change_order(page,count,serch,order,desc)
  {
      $("#serch_"+order).empty();
            switch (order) 
            {
              case 'appId':
                link='По номеру анкеты';
                break
              case 'results':
                link='По результатам';
                break
              case 'email':
                link='По email';
                break
              default:
                link='По фамилие';
            }  
            set_serch_div(order,page,serch,!desc,link,count);
         ajax_result(page,count,serch,order,desc);   
  }
  
function send_mail()
{
    checkbox=$(":checkbox[name^=emails]:checked").map(function () { return $(this).val(); }).get();
    dat = {"emails" : checkbox};
    $.ajax({
    type: "POST",
    url: "sendMailToStudent.html",
    data: dat,
    dataType : "json",
    success: function(data,textStatus)
    {
         $("#serch_info").append("Вы отправили "+data.count+" писем");
    }
    });
    return false;
}