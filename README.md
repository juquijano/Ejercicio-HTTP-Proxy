# Ejercicio-HTTP-Proxy
>
>El códico consta de dos clases: Main y ProxyThread.
>
>La clase Main inicia el servidor socket en el puerto determinado (por defecto es el 8080) donde queda esperando por una nueva conexión. Cuando una petición es realizada, se genera un nuevo ProxyThread al que se le pasa el socket que inicia su ejecución.
>
>ProxyThread class recibe la solicitud (request) proveniente del cliente, loguea la información y se la envía al servidor real. Luego recibe la respuesta y propaga la misma al cliente, pudiendose loguear también la respuesta del servidor real (esta línea está comentada en el código).

# ¿Cómo utilizarlo?
>Primero, asegúrese tener instalado Java en su máquina. 
>En caso de no, consulte [esta página](https://www.java.com/es/download/).
>
>Luego descargue el archivo [JuQujano-HTTP-Proxy.jar](https://github.com/juquijano/Ejercicio-HTTP->Proxy/raw/master/out/artifacts/JuQujano_HTTP_Proxy_jar/JuQujano-HTTP-Proxy.jar) (el mismo se encuentra en /out/artifacts/JuQujano_HTTP_Proxy_jar/ de este repositorio).

>**Por consola ejecute:**
>
>`java -jar JuQujano-HTTP-Proxy.jar <puerto>`
>
>En caso de no especificar un puerto, se usará el 8080 por defecto.

>**En el navegador:**
>Configure el proxy de su browser para conectarse a *127.0.0.1* puerto *8080* o el puerto que ingrese al ejecutar el jar.
