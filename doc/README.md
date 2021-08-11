# bytehonor-netty-sdk
bytehonor-netty-sdk

## 协议

| head       | length    |  data     |  check    |  end      |  
| :--------  | :-------- | :-------- | :-------- | :-------- |  
| 0x88       | 0x0001    |  bytes[]  |  0x01     |  0x99     |  

```
length = data + check + end
check  = length + data
```

