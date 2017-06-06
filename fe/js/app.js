function todoItem(id, todo, completed, date) {
    this.id = id;
    this.todo = todo;
    this.completed = completed;
    this.date = date;

}

var tosolist = [{}];

// 전체로드
$(window).load(function() {

	var filterFlag = 'selectAll'; // selectAll selectCompleted selectActive


		readAll();
		//fiterActive();
    // 함수 .
    // Task5 갯수세기 - completed = 0 일의 갯수를 보여준다.
    function countUncompleted() {
        $.ajax({
            url: '/api/todos/count',
            type: 'get',
            dataType: 'json',
            contentType: "application/json",
            success: function(data) {

                $('.todo-count').find('strong').html(data);
            },
            error: function(request, status, error) {
                alert("error to count");
            }
        });
    }
    // TASK1 : 다 다운받기
    function readAll() {
        $('.todo-list').empty();
        $.getJSON('/api/todos', function(data) {
            $.each(data, function(key, value) {
                //$('body').append('<h1>' + value.name + ' : ' + value.unitPrice + '</h1>');
                if (value.completed == 0) {
                    $('.todo-list').append('<li><div class="view" id="' + value.id + '"><input class="toggle" type="checkbox"><label>' + value.todo + '</label><button class="destroy"></button></div><input class="edit" value="Rule the web"></li>');
                } else {
                    $('.todo-list').append('<li class="completed"><div class="view" id="' + value.id + '"><input class="toggle" type="checkbox" checked><label>' + value.todo + '</label><button class="destroy"></button></div><input class="edit" value="Create a TodoMVC template"></li>');
                }
                countUncompleted();
            });
        });
    }

    //TASK2 : 새 아이템 입력
    $('.new-todo').keypress(function(e) {
        if (e.which == 13) {
            var value = $('.new-todo').val();

            if (value == "") {
                alert("no value")
            } else {
                $.ajax({
                    url: '/api/todos',
                    type: 'post',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify({
                        todo: value,
                        completed: 0
                    }),
                    success: function(data) {
                        //alert("Data Save: " + data.id);
                        $('.todo-list').prepend('<li style="list-style:none" class="listItem" ><div class="view" id="' + data.id + '"><input class="toggle" type="checkbox"><label>' + data.todo +
                            '</label><button class="destroy"></button></div><input class="edit" value="Create a TodoMVC template"></li>');
                        countUncompleted();
												$('.new-todo').val('');
                    }
                });
            }
        }
    });

    // TASK3 : 동적 이벤트 처리 - 토글 삭제
    $(document).on('change', '.toggle', function() {

        var getId = $(this).closest('div').attr('id');
        //alert(completed + " " + getId);
        //0
        var completedCheck;

        // 1.2 그 아이디로 검색하기 - ajax update
        $.ajax({
            url: '/api/todos/' + getId,
            type: 'get',
            dataType: 'text',
            success: function(data) {

                data = eval('([' + data + '])');

                completedCheck = data[0];

                $.ajax({
                    url: '/api/todos/' + getId,
                    type: 'put',
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify({
                        todo: completedCheck.todo,
                        completed: completedCheck.completed,
                        date: completedCheck.date
                    }),
                    success: function(data) {
                        //  alert("success : ")
                        countUncompleted();
                    },
                    error: function(request, status, error) {
                        alert("error to update");
                    }
                });

            },
            error: function(request, status, error) {
                alert("error to search by id");
            }
        });

        // 1.2 changedData-Completed Check
        // 2.1 css의 completed 클래스가 있으면 없애고 없으면 만든다. 클래스 검색 찾아서 넣기
        // 2.2 리스트에서 취소선이 그어진다.
        if ($(this).closest("li").hasClass("completed") === true) {
            // 속성값이 존재하면 completed = 1 (수행됨)이라는 거니까
            $(this).closest("li").removeClass("completed");
        } else if ($(this).closest("li").hasClass("completed") === false) {
            // 속성값이 존재하지 않으면 = 0 (수행안됨)이라는 거니까
            $(this).closest("li").addClass("completed");
        }

    });
    //Task4 삭제
    $(document).on('click', '.destroy', function() {
        var getId = $(this).closest('div').attr('id');

        $.ajax({
            url: '/api/todos/' + getId,
            type: 'delete',
            dataType: 'text',
            success: function(data) {
                // 아이템 삭제하기

            }
        });

        $(this).closest('.view').parent().remove();
        countUncompleted();
    });

    // TASK7  일괄 삭제
    $(document).on('click', '.clear-completed', function() {
        $.ajax({
            url: '/api/todos',
            type: 'delete',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({}),
            success: function(data) {
							$('.todo-list').children('li').filter('.completed').empty();
            }
        });



    });


		// TASK6 : 태그 선택시 리스트 변화
		$(document).on('click', 'a', function(event) {
				event.preventDefault();
				var selectedTag = $('.todo-list > li');
				var getId = $(this).attr('id');
				filterFlag = getId;

				// 선택 다 초기화하고 새 버튼 클릭 값 준다
				$('.filters > li > a').removeClass('selected');
				$(this).addClass('selected');
				fiterActive();

		});


		// 필터플레그 대로 보여주기
		function fiterActive(){

			if (filterFlag === 'selectAll') {
					//readAll();
					$('.todo-list > li').fadeIn(100); // 다 페이드인
					//filterFlag = 'all'; // 선택한 상태값으로 저장되니까 상관 없나.
			} else if (filterFlag === 'selectCompleted') {

					$('.todo-list > li').hide();
					$('.todo-list').children('li').filter('.completed').fadeIn(100);
					//filterFlag = 'com';

			} else { // active만 보여준다.
					$('.todo-list').children('li').hide();
					$('.todo-list').children('li').not('.completed').fadeIn(100);
					//filterFlag = 'act';
			}
		}

});
