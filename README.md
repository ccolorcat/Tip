# Tip

适用 Android 开发，用于网络错误、内容为空等的提示。

## 1. 用法举例

```java
mTip = Tip.from(this, R.layout.network_error, this);
mTip.showTip(); // 显示提示
mTip.hideTip(); // 隐藏提示
```

## 2. 使用方法

(1) 在项目的 build.gradle 中配置仓库地址：

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

(2) 添加项目依赖：

```groovy
	dependencies {
	        implementation 'com.github.ccolorcat:Tip:v1.0.0'
	}
```

## 3. 版本历史

v1.0.0

> 首次发布