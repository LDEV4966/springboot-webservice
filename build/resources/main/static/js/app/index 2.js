var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();

// index.js의 첫 문장에 var main = {...} 라는 코드를 선언했다. 굳이 index라는 변수의 속성으로 function을 추가한 이유는 ?
// 브라우저 스코느는 공용공간으로 쓰이기 때문에, 위와 같이 index.js의 유효범위를 만들어 사용하지 않는다면,  나중에 로딩된 js의 동일이름의 함수가 선언되면
// 기존의 함수를 덮어쓴다. 이렇게 var index 이란 객체를 만들어 해당 객체에서 필요한 모든 function을 선언하면 index객체안에서만 함수가 유효하여 다른 js 와 겹칠일이 없다.
// 이런식의 스코프관리 문제로 ES6를 비롯한 최신 프레임워크에서는 이런기능을 프레임워크 레벨에서 지원된다.
