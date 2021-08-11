# bytehonor-netty-sdk
bytehonor-netty-sdk

## 协议

| head       | type       | length    |  data     |  check    |  end      |  
| :--------  | :--------  | :-------- | :-------- | :-------- | :-------- |  
| 0x24       | 0x01       | 0x0001    |  bytes[]  |  0x0001   |  0x26     |  
| 1 byte     | 1 byte     | 2 byte    |  * byte   |  2 byte   |  1 byte   |  

```
length = data + check + end
check  = sum(type + length + data)

0x24 = $
0x26 = &
```

