<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="biz.cedriclevasseur.mcexample.Mc"%>
<%@page import="biz.cedriclevasseur.mcexample.Traitement"%>
<!DOCTYPE html>
 <html lang="en">
 <head>
   <meta charset="utf-8">
   <title>Populate input field from a selectbox example</title>
 </head>
 <body>
 
   <% //A simple traitement which return a List of Mc
      // Mc is a basic object containing a name, phone, fax number
   List<Mc> listOfMc = Traitement.doTraitement(); %>

 
 <select id="mc">
</select>

<input type="text" id="phone" />
<input type="text" id="fax" /> 

 
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script>
     $(document).ready(function(){


              
// Example of generated array
//			   var mcvalues = {
//		  "mcvalues": [
//                        { 
//			  "name": "MC1",
//			  "phone": "100000000",
//			  "fax": "1222222222"
//			},
//			{ "name": "MC2",
//			  "phone": "2000000000",
//			  "fax": "2222222222"
//			},
//          {  "name": "MC3",
//			  "phone": "3000000000",
//			  "fax": "3222222222"
//			}]
//		};
        
        var mcvalues = { "mcvalues" : <%=Arrays.toString(listOfMc.toArray())%> };
        

		
		//populate the selectbox	
		var options = $("#mc");
		$.each(mcvalues.mcvalues, function() {
			options.append($("<option />").val(this.name).text(this.name));
		});
		
		// populate input box on change
                $("#mc").change(function() {
                        var obj=findByKeyValue(mcvalues.mcvalues, 'name', this.value);
			$('#phone').val(obj.phone);
			$('#fax').val(obj.fax);;
		});


            });
            
            function findByKeyValue(arr, key, value)
            {
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i][key] == value) {
                        return arr[i];
                    }
                }
                return null;
            }
   </script>
 </body>
 </html>