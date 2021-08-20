# bytehonor-netty-sdk
bytehonor-netty-sdk

## 协议

| head       | type       | length      |  data     |  check       |  end      |  
| :--------  | :--------  | :---------  | :-------- | :----------  | :-------- |  
| 0x24       | 0x01       | 0x00000001  |  bytes[]  |  0x00000001  |  0x26     |  
| 1 byte     | 1 byte     | 4 byte      |  * byte   |  4 byte      |  1 byte   |  

```
length = data + check + end
check  = sum(type + length + data)

0x24 = $
0x26 = &
```

