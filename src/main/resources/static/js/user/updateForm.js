$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      "userId": $("#userId").val(),
      "password": $("#password").val(),
      "name": $("#name").val(),
      "email": $("#email").val()
    }
    const id = $("#id").val()
    const urlPath = "/users/" + id + "/update"

    $.ajax({
      type: 'put',
      url: urlPath,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
    }).done(function () {
      alert("회원정보 수정이 완료되었습니다.")
      location.href = "/user/login"
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      // 유저 이메일 중복
      if (errorResponse.name === 'ALREADY_EXIST_EMAIL') {
        $("#emailError").text(errorResponse.errorMessage)
      }
      // 유저 입력 형식 오류
      if (errorResponse.name === 'INVALID_INPUT_FORMAT') {
        errorResponse.errors.forEach(item => {
          $(`#${item.field}Error`).text(item.message)
        })
      }
    })
  })

  function clearErrorMessage() {
    $("#form p").text("")
  }

  function hasFormatError(respMap) {
    for (let key in respMap) {
      const value = respMap[key]
      if (value.errorCode !== undefined) {
        return true;
      }
    }
    return false;
  }

  function writeError(respMap) {
    for (let key in respMap) {
      const value = respMap[key]
      $(`#${key}Error`).text(value.errorMessage)
    }
  }
})
