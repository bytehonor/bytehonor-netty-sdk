# bytehonor-netty-sdk
bytehonor-netty-sdk

## 协议

| head       | length    |  data     |  check    |  end      |  
| :--------  | :-------- | :-------- | :-------- | :-------- |  
| 0x88       | 0x0001    |  bytes[]  |  0x0001   |  0x99     |  
| 1 byte     | 2 byte    |  * byte   |  2 byte   |  1 byte   |  

```
length = data + check + end
check  = length + data
```

