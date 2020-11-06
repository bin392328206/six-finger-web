> 这个系列其实就是很多个轮子的总和，那小六六也不能说一下子就写完，写完还得经常的重构呢。所以这个项目肯定也是一个长期更新的项目，当然作者的水平肯定是有限的，所以如果出现问题欢迎大家给我提Issues 或者给我提pr，希望有更多的人一起参与进来一起，最后如果这个项目对您有帮助，感恩您给个star，万分感谢，文章所有的搭建过程我会同步写博客在我的six-finger里面 

**也可以关注我的公众号（六脉神剑的程序人生） 回复 six-finger-web 获取，文末有二维码图片**。

# six-finger-web
一个Web后端框架的轮子从处理Http请求【基于Netty的请求级Web服务器】 到mvc【接口封装转发)】，再到ioc【依赖注入】，aop【切面】，再到 rpc【远程过程调用】最后到orm【数据库操作】全部自己撸一个（简易）的轮子。

## 为啥要写这个轮子
其实是这样的，小六六自己平时呢？有时候喜欢看看人家的源码比如Spring,但是小六六的水平可能不怎么样，每次看都看得晕头转向，然后就感觉里面的细节太难了，然后我就只能观其总体的思想，然后我就想我如果可以根据各位前辈的一些思考，自己撸一个简单的轮子出来，那我后面去理解作者的思想是不是简单点呢？于是呢 six-finger-web就面世了，它其实就是我的一个学习过程，然后我把它开源出来，希望能帮助那些对于学习源码有困难的同学。还有就是可以锻炼一下自己的编码能力，因为平时我们总是crud用的Java api都是那些，久而久之，很多框架类的api我们根本就不熟练了，所以借此机会，锻炼一下。

> 其实我写的这个轮子肯定比不上Spring这样的巨作，而且我的目的也不是说成为企业级的框架，我呢，只是为了学习这些巨作。

## 特点
- 内置由 Netty 编写 HTTP 服务器，无需额外依赖 Tomcat,Jetty 之类的 web 服务
- 代码简单易懂（小六六自己写不出框架大佬那种高类聚，低耦合的代码），能力稍微强一点看代码就能懂，弱点的也没关系，小六六有配套的从0搭建教程。
- 支持MVC相关的注解确保和SpringMVC的用法类似
- 支持Spring IOC 和Aop相关功能
- 支持类似于Mybatis相关功能
- 支持类似于Dubbo的rpc相关功能
- 对于数据返回，只支持Json格式


## 规划和完成情况
小小六六把它分为5大部分，每个部分有专门的搭建过程，和解析博客。然后小六六自己也是一步一步的来完成，最后来完善（可能博客和源码有不同的地方，毕竟每次的重构不一定会记得修改原来的文章）

### Netty 编写 HTTP 服务器

> 相关博客
下面是Http轻量级服务器的实现博客
- [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（一）](https://juejin.im/post/6883005240740675591/)
### SpringMVC 

> 相关博客
>下面是类似于简单的MVC实现的博客
- [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（二）](https://juejin.im/post/6884027512343494669)

### Spring

#### ioc
> 第一篇ioc
- [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（三）](https://juejin.im/post/6885224199938867213)

> 第二篇ioc
- [ [适合初中级Java程序员修炼手册从0搭建整个Web项目]（四）](https://juejin.im/post/6885994038278193165)
#### aop
- [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（五）](https://juejin.im/post/6888926292285063176/)

> 相关博客

### RPC

> 第一篇 入门rpc
- [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（六）](https://juejin.im/post/6889627826451021831)

> 第二篇 SPI 相关
- [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（七）](https://juejin.im/post/6890406233061195789/)

> RPC 实战
- - [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（八）](https://juejin.im/post/6891095158201745416)
  - [[适合初中级Java程序员修炼手册从0搭建整个Web项目]（九）](https://juejin.im/post/6891836391076921352/)

### ORM

> 相关博客


## 如何构建和测试
- 第一步当然是fork或者code代码到本地，然后用你的开发工具打开，下载maven所需要的依赖。
- 找到如下图所在的目录，然后找到我们的启动类，在demo文件下的server里面
![](media/images/1.png)
- 然后下面的1业务代码测试，其实就是和我们平时代码一样了 controller,service等等，然后我们要找我们的请求路径就去下图找
![](media/images/2.png)


## 公众号二维码
![](media/images/公众号.png)

