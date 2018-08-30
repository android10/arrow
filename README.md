

Arrow
=========================

[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-java-green.svg)](http://www.oracle.com/technetwork/indexes/downloads/index.html)
[![Download](https://api.bintray.com/packages/android10/maven/arrow/images/download.svg) ](https://bintray.com/android10/maven/arrow/_latestVersion)

```Arrow``` is Lightweight library toolbox for Java and Android Development. Basically it groups utilites and helper classes commonly used for mobile development. Most of them have been extracted from popular java libraries like ```Guava``` or ```Apache Commons``` plus other stuff added myself. Refer to the Main Features section for a fully explanation of its capabilities. 

![arrow](https://cloud.githubusercontent.com/assets/1624385/13253864/9852185c-da3e-11e5-93ff-e7d21e130130.jpg)


# Features:

- **```Optional<T>```:** An immutable object that may contain a non-null reference to another object. Each instance of this type either contains a non-null reference, or contains nothing (in which case we say that the reference is "absent"); it is never said to "contain null". <a href="https://github.com/google/guava/wiki/UsingAndAvoidingNullExplained#optional" target="_blank">Refer to Guava ```Optional<T>``` documentation</a> or <a href="http://fernandocejas.com/2016/02/20/how-to-use-optional-on-android-and-java/" target="_blank">How to use ```Optional<T>``` on Java and Android.</a>

- **```Strings```:** Static utility methods pertaining to String or CharSequence instances. <a href="https://github.com/google/guava/wiki/StringsExplained" target="_blank">Refer to Guava ```Strings``` documentation.</a>
 
- **```Preconditions```:** Static convenience methods that help a method or constructor check whether it was invoked correctly (whether its preconditions have been met). These methods generally accept a boolean expression which is expected to be true (or in the case of checkNotNull, an object reference which is expected to be non-null). When false (or null) is passed instead, the Preconditions method throws an unchecked exception, which helps the calling method communicate to its caller that that caller has made a mistake. <a href="https://github.com/google/guava/wiki/PreconditionsExplained" target="_blank">Refer to Guava ```Preconditions``` documentation.</a>
 
- **```Collections```:** Provides static methods for working with Collection instances. <a href="https://github.com/google/guava/wiki/CollectionUtilitiesExplained" target="_blank">Refer to Guava ```Collections``` documentation.</a>
  - ```FilteredCollection.class```
  - ```Lists.class```
  - ```CollectPreconditions.class```
  - ```Iterables.class```
  - ```Iterators.class```
  - ```MoreArrays.class```
  - ```MoreCollections.class```
   
- **```MoreObjects```:** Helper functions that operate on any Object, and are not already provided in ```Objects```. <a href="https://github.com/google/guava/wiki/CommonObjectUtilitiesExplained" target="_blank">Refer to Guava ```MoreObjects``` documentation.</a>  
 
- **```Annotations```:** These annotations has no side effect in your code, they are only used for decoration purpose, in case you need either to highlight it or to denotate it.
  - ```@Beta```: Class, method or field is declared as Beta functionality
  - ```@Experimental```: Class, method or field is declared as Experimental
  - ```@Issue```: Class, method or field is linked to an issue. A ref value can be added to specify more information.
  - ```@See```: Class, method or field is linked to a link. A ref value should be added in order to specify the link url.
  - ```@VisibleForTesting```: Class, method or field has its visibility relaxed, so that it is more widely visible than otherwise necessary to make code testable.
  - ```@WorkInProgress```: Class, method or field is declared as work in progress.


# Using Arrow

To enable Arrow, add your dependency in your ```build.gradle``` file:

```java
repositories {
  jcenter()
}

dependencies {
  compile 'com.fernandocejas:arrow:{latest_version}'
}
```


# License

    Copyright 2016 Fernando Cejas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


![http://www.fernandocejas.com](https://github.com/android10/Sample-Data/blob/master/android10/android10_logo_big.png)

<a href="https://www.buymeacoffee.com/android10" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>
