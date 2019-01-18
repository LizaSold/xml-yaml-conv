# xml-yaml-conv
Simple service converting XML code to YAML

# Usage

Firstly, build the project
`./gradlew build`

To see the javadoc in HTML use 
`./gradlew createJavadoc`
Documentation will be added to `/javadoc` folder


Then, login as root user `sudo -i` and build docker 
`./gradlew build docker`

Then, run docker
`docker run -p 8080:8080 com.eliza/xmlyamlconv`

# Example
Make POST request on `http://localhost:8080/convert` using POSTMAN client
![Screenshot](https://pp.userapi.com/c849320/v849320297/10c4b5/FUiEwtN_3xE.jpg)

Internal text:
```xml
<?xml version="1.0" encoding="UTF-8"?> 
<blacklistentries> 
  <blacklist> 
    <subnet>1.2.3.4</subnet> 
    <description>a single IP address</description> 
  </blacklist> 
</blacklistentries>
```

Result:
```yaml
blacklistentries:
  blacklist:
    subnet: "1.2.3.4"
    description: "a single IP address"
```
