# hotpatch
基于java Instrumentation 进行热替换

java agent就可以不用修改原有的java程序代码，通过agent的形式来修改或者增强程序，对于整个类的结构修改，仍然需要重启虚拟机。

使用方法：
1.在部署程序的根目录下新建文件夹hotfiles
2.将需要更新的文件放在hotfiles下面，注:包名以文件夹的形式和类文件一起放进去
3.在启动的文件中添加javaagent：
java -javaagent:hotpatch-0.0.1-SNAPSHOT.jar
