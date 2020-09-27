<img src="/assets/logo.png" align="right" />

# PLAYme <img src="/assets/Build with love.svg"/><br>
> PLAYme is a sample app that shows all the video of your phone.<br>
<img src="/assets/studio version.svg"/> <img src="/assets/minSdk.svg"/> <img src="/assets/compileSdk.svg"/> <img src="/assets/gradle version.svg"/> <img src="/assets/java version.svg"/>
## What does it do?
It is a very straightforward app with a single screen.
<br>The screen displays a list of videos of your phone and even you can bookmark your videos.
## Appearance?
<p align="center">
    <img src="/assets/snap.jpg" alt="Screenshots"  height="650" width="330"/>
</p>

## Project technical description
* This app follows the **Model-View-ViewModel** architecture with clean code pattern
* **Depending injection** is achieved by using Android Hilt
* This app essentially serve the **Single responsibility** principle
* Using  **observer pattern **data is provide to **View**
* This app uses a combination of **package-by-feature** and **package-by-layer**. The top level packages are feature based. Inside each **feature-package**, the code is split into different packages based on the layer
* **Unit testing** is also performed

## Architecture Package Structure
> app
>> java
>>> com.player.home<br>
>>>> application<br>
>>>> core<br>
>>>> permissions<br>
>>>> di<br>
>>>> extension<br>
>>>> utils<br>
>>>> home<br>
>>>>> di<br>
>>>>> model<br>
>>>>> ui<br>
>>>>> viewmodel<br>

### Libraries Used
* [Support Library]
* [android Ktx Core]
* [Exoplayer]
* [Hilt]
* [Life Cycle ViewModel]
* [LiveData]
* [Junit]
* [Mockito]

## License
```
Copyright 2020 Devendra Mehra

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
