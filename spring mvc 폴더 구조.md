## spring mvc 폴더 구조

![image](https://user-images.githubusercontent.com/30086555/159111589-9773b6d6-39a6-4304-8303-cf00b608f16f.png)
+ model 디렉터리는 Domain Entity 객체들이 공통적으로 사용할 객체들로 구성됩니다. 대표적으로 Embeddable 객체, Enum 객체 등이 있습니다.
<br>
+ 디렉토리 구조 설정  

<hr width = "100%" color = "grey" >
<div align="center">
* api -> <b>controller</b><br>
* application -> <b>service</b><br>
* <b>dao</b>(data asscess object)<br>
데이터베이스의 작업(입력, 받아오기, 수정, 삭제 등)의 작업을 정의한 클래스  <br>
* <b>domain</b><br>
도메인 엔티티에 대한 클래스로 구성  <br>
* <b>dto</b>(data transfer object)  <br>
각 컬럼항목을 저장하기 위한 필드 변수와 get,set 메서드로만 정의된 클래스  <br>
* <b>exception</b><br>
해당 도메인이 발생시키는 Exception 으로 구성  <br>
</div>
<br>

<p align="center"><small>(위의 디렉토리의 구조는 임의로 작성함! 팀원들과 의견 및 조율 필요~ )</small></p>
<hr width = "100%" color = "grey" align="center" style="margin: auto;">

<br>
<br>

<small>* 참고 :  https://cheese10yun.github.io/spring-guide-directory/     (전체적인 구조)</small><br>
<small>* 참고 :  https://wonos.tistory.com/457     (각 구조 설명 매우 잘되어 있음!)</small>
