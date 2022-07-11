# netty-beautify-sdk
netty-beautify-sdk

bytehonor protocol

## error
```
2022-07-11 21:32:50.489 INFO  --- [pool-4-thread-1] c.b.s.b.netty.common.task.NettyServerCheckTask    : [24] channel size:1, subscribe size:2, whois size:1/1
2022-07-11 21:32:50.490 INFO  --- [pool-4-thread-1] c.b.s.b.netty.common.task.NettyServerCheckTask    : [33] subject:com.bytehonor.sdk.service.remote.twitter.vo.TwitterUserFriendsResult, channels size:1
2022-07-11 21:32:50.490 INFO  --- [pool-4-thread-1] c.b.s.b.netty.common.task.NettyServerCheckTask    : [33] subject:com.bytehonor.sdk.service.remote.twitter.vo.TwitterUserTweetsResult, channels size:2
2022-07-11 21:32:50.492 WARN  --- [pool-4-thread-1] c.b.s.b.netty.common.task.NettyServerCheckTask    : [36] remove subject:com.bytehonor.sdk.service.remote.twitter.vo.TwitterUserTweetsResult, channel:00163efffe0122d8-00007bfb-00000002-085db8cb75af7069-59fbd1f7
2022-07-11 21:32:50.492 ERROR --- [pool-4-thread-1] c.b.sdk.beautify.netty.common.task.NettyTask      : [22] NettyTask error
java.util.ConcurrentModificationException: null
        at java.util.HashMap$HashIterator.nextNode(HashMap.java:1445)
        at java.util.HashMap$KeyIterator.next(HashMap.java:1469)
        at com.bytehonor.sdk.beautify.netty.common.task.NettyServerCheckTask.runInSafe(NettyServerCheckTask.java:34)
        at com.bytehonor.sdk.beautify.netty.common.task.NettyTask.run(NettyTask.java:20)
        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
        at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:308)
        at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$301(ScheduledThreadPoolExecutor.java:180)
        at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:294)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)
```
