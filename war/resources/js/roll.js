function toggle(id)
{
        var e = document.getElementById(id);
        var dh = gh(id);
        var elems = e.getElementsByTagName('*');
        
        if (e.style.display == "none")
        {
                for(var i=0; i<elems.length; i++){vhe(elems[i], "hidden");}
        
                e.style.height="1px";
                e.style.display = "block";
                for(var i=0;i<=100;i+=5)
                {
                        (function()
                                {
                                        var pos=i;
                                        setTimeout(function(){e.style.height = (pos/100)*dh+1+"px";},pos*5);
                                }
                        )();
                }
                setTimeout(function(){for(var i=0; i<elems.length; i++){elems[i].style.visibility="visible";}},500);
                return true;
        }
        else
        {
                var lh=dh-1+"px";
                
                for(var i=0; i<elems.length; i++){vhe(elems[i], "hidden");}
                
                for (var i=100;i>=0;i-=5)
                {
                        (function()
                                {
                                        var pos=i;
                                        setTimeout(function()
                                        {
                                                e.style.height = (pos/100)*dh+"px";
                                                if (pos<=0)
                                                {
                                                        e.style.display = "none";
                                                        e.style.height=lh;
                                                }
                                        },1000-(pos*5));
                                }
                        )();
                }
                return true;
        }
        return false;
}
function vhe(obj, vh){obj.style.visibility=vh;}
function gh(id)
{
        var e = document.getElementById(id);
        if(e.style.display == "none")
        {
                e.style.visibility = "hidden";
                e.style.display = "block";
                dh = e.clientHeight||e.offsetHeight+5; // Высота
                e.style.display = "none";
                e.style.visibility = "visible";
        }
        else
        {
                dh = e.clientHeight||e.offsetHeight+5; // Высота
        }
        return dh;
}