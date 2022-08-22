<template>
  <label class="form-label" for="address">주소</label>
  <i class="fas fa-key fa-lg me-3 fa-fw"></i>
  <div class="form-outline flex-fill mb-0">
    <input
        type="text" readonly
        class="form-control-plaintext"
        id="zipcode"
        :value="zipcode"
        @change="this.$emit('input', this.zipcode)"
        placeholder="우편번호"
    />
  </div>
  <div class="form-outline flex-fill mb-0">
    <button type="button" class="btn btn-primary" @click="popUpPostCodeSearchForm()">주소검색</button>
  </div>
  <div class="form-outline flex-fill mb-0">
    <input
        type="text" readonly
        class="form-control"
        id="address"
        :value="address"
        placeholder="기본주소"
    />
    <input
        type="text"
        class="form-control"
        id="addressDetails"
        :value="addressDetails"
        @input="inputAddressDetails"
        placeholder="나머지 주소(선택 입력 가능)"
    />
  </div>
</template>
<script>
export default {
  inheritAttrs: false,
  data() {
    return {
      zipcode: '',
      address: '',
      addressDetails: ''
    }
  },
  mounted() {
    let script = document.createElement('script')
    script.setAttribute('src', 'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js')
    document.head.appendChild(script)
    // for checking whether it's loaded in windows are not, I am calling the below function.
  },
  methods: {
    popUpPostCodeSearchForm() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          if (this.extraAddress !== "") {
            this.extraAddress = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            this.address = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.address = data.jibunAddress;
          }

          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if (data.userSelectedType === "R") {
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extraAddress += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extraAddress +=
                  this.extraAddress !== ""
                      ? `, ${data.buildingName}`
                      : data.buildingName;
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (this.extraAddress !== "") {
              this.extraAddress = `(${this.extraAddress})`;
            }
          } else {
            this.extraAddress = "";
          }
          // 우편번호를 입력한다.
          this.zipcode = data.zonecode;
          this.emitChangeAddressToParentComponent()
        },
      }).open();
    },
    inputAddressDetails(e) {
      this.addressDetails = e.target.value;
      this.emitChangeAddressToParentComponent();
    },
    emitChangeAddressToParentComponent() {
      this.$emit('changeAddress', {
        zipcode: this.zipcode,
        address: this.address,
        addressDetails: this.addressDetails
      })
    }
  }
}
</script>