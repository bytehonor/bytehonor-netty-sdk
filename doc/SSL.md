# 自生成正式

首先进入目录 D:\test
（随意）

密码都是 123456

## 1、生成.key文件
命令

`openssl genrsa -des3 -out server.key 2048`

中间会提示输入密码(重复输入两次)，要记住这个密码；

这时会在D:\test目录下生成server.key文件。

## 2、生成.crt文件
命令

`openssl req -new -x509 -key server.key -out server.crt -days 3650`

会提示输入server.key的密码

```
开始输入Country Name：CN
State or Province Name：SH
Locality Name：shanghai
Organization Name：Lmm
Organizational Unit Name：Lmm
Common Name：Lmm
Email Address：lmm@lmm.com
```

这时会在D:\test目录下生成server.crt文件。

## 3、生成.pfx文件
命令

`openssl pkcs12 -export -out server.pfx -inkey server.key -in server.crt`

提示输入server.key文件的密码

提示输入即将生成的.pfx文件的密码(需要输入两次)

这时会在D:\test目录下生成server.pfx文件。


## 4、生成.jks文件
命令

`keytool -importkeystore -srckeystore server.pfx -destkeystore server.jks  -srcstoretype PKCS12 -deststoretype JKS`

提示输入文件的密码

这时会在D:\test目录下生成server.jks文件。


## 5、参考

http://slproweb.com/products/Win32OpenSSL.html

https://blog.csdn.net/hu_344/article/details/73368774

https://blog.csdn.net/luo15242208310/article/details/108195803

https://blog.csdn.net/luo15242208310/article/details/108215019

