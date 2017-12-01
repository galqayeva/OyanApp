<html>
    <body>
        <h1>JAX-RS File Upload Example</h1>
        <form action="webresources/user/photoupload" method="post" enctype="multipart/form-data">
            <p>
                User Token : <input type="text" name="token" />
            </p>
            <p>
                File name : <input type="text" name="fileName" />
            </p>
            <p>
                Choose the file : <input type="file" name="selectedFile" />
            </p>
            <input type="submit" value="Upload" />
        </form>
        http://www.howtodoinjava.com
    </body>
</html>
