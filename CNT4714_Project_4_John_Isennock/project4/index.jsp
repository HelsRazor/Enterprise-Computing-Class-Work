<!DOCTYPE html>

 <%
    String sqlStatement = (String)session.getAttribute("sqlStatement");
    if(sqlStatement == null) sqlStatement = "select * from suppliers";
    String message = (String)session.getAttribute("message");
    if(message == null) message = " ";
%>

<html lang="en">

<head>
    <title>Project 4</title>
    <meta charset="wtf-8">
    <style type = "text/css">
        <!--
        body{background-color: blue; color:white; text-align: center;}
        h1{color:white; 28pt;}
        h2{color:yellow; font-size: 16pt;}
        p{color:white; font-size: 12pt;}
        body{background-color: blue; text-align: center;}
        textarea{background:black;color:lime; fontsize:14;}
        input{background-color: black; color: yellow; font-weight:bold;}
        table{border:5px solid black;}
        th,td {padding: 5px; border: 1px solid black;}
        #bl{color:708090;}
        -->
        .buttons{margin-bottom: 20px; background-color: black;color:yellow}
    </style>
    <script type="text/javascript">
        function eraseText(){
            document,getElementById("cmd").innerHTML = " ";
        }
    </script>
    
</head>

<body>
    
        

    <h1>Welcome to the Project 4 Spring 2021 Enterprise Database System</h1>
    <h1>A Servlet/JSP-based Multi-tiered Enterprise Application Utilizing A Tomcat Container</h1>
    <h2>Developed By: John Isennock</h2>
    <hr size="2" width="100%" color="white">
    <p>You are connected to the Project 4 Enterprise System database as an Administrator<br />
        Please enter any valid SQL query or update command.<br /></p>
    <form action = "/project4/MySQLServlet" method="post">
        <textarea name="sqlStatement" id="cmd" rows="10" cols="75" ><%=sqlStatement%></textarea><br><br>
        <input type="submit" value="Execute Command"/>
        <input type="reset" value="Clear Form" onclick="javascript:eraseText();"/>
    </form>
        <p><br />All execution results will appear below.</p>
        <hr size="2" width="100%" color="white">
        <center> 
            <p>
                <b>Database Results:</b><br>
                    <table>
                        <%=message%>
                    </table>
            </p>
        </center>



</body>


</html>