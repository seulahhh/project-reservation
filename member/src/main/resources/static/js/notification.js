
    const managerId = document.getElementById("connectSource").dataset
        .managerId;

    console.log("managerId: " + managerId);
    // SSE 연결 설정
    const eventSource = new EventSource(`/sse-connect/${managerId}`);

    // 연결이 열렸을 때
    eventSource.onopen = function(event) {
        console.log('SSE 연결이 열렸습니다.');
    };

    // 메시지를 수신했을 때
    eventSource.onmessage = function(event) {
        console.log('알림 수신:', event.data);
        showNotification(event.data);
    };

    // 특정 이벤트 이름으로 메시지 수신 (예: 'alarm')
    eventSource.addEventListener('alarm', function(event) {
        console.log('알림 수신 (alarm):', event.data);
        showNotification(event.data);
    });

    // 에러가 발생했을 때
    eventSource.onerror = function(event) {
        console.error('SSE 에러 발생:', event);
        // 연결을 닫고 재연결 로직을 추가할 수 있습니다.
        eventSource.close();
        // 재연결 시도 예시 (5초 후)
        setTimeout(function() {
            reconnectSSE();
        }, 500);
    };

    // 알림 표시 함수
    function showNotification(message) {
        const notificationDiv = document.getElementById('notification');
        let stringMsg = JSON.parse(message);

        notificationDiv.classList.add('on');

        const dateMsg = `날짜: ${stringMsg.reservationTime[0]}년 ${stringMsg.reservationTime[1]}월 ${stringMsg.reservationTime[2]}일`
        const countMsg = `${stringMsg.guestCount}명`;
        notificationDiv.getElementsByClassName("reservation-info").innerText = `${dateMsg} + ${countMsg}`;
    }

    // SSE 재연결 함수
    function reconnectSSE() {
        console.log('SSE 재연결을 시도합니다.');
        const newEventSource = new EventSource(`/sse-connect/${managerId}`);

        newEventSource.onopen = function(event) {
            console.log('SSE 연결이 다시 열렸습니다.');
        };

        newEventSource.onmessage = function(event) {
            console.log('알림 수신:', event.data);
            showNotification(event.data);
        };

        newEventSource.addEventListener('alarm', function(event) {
            console.log('알림 수신 (alarm):', event.data);
            showNotification(event.data);
        });

        newEventSource.onerror = function(event) {
            console.error('SSE 에러 발생:', event);
            newEventSource.close();
            setTimeout(function() {
                reconnectSSE();
            }, 500);
        };

        function hide(el) {
            el.classList.add("off");
            el.classList.remove("on");
        }
    }