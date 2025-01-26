/**
 * GeoLocation을 사용하여 사용자 좌표 조회
 */
async function getLocation() {
    let status = "";
    document.getElementById('listContainer').remove();
    document.getElementById('dynamicContainer').style.display = 'block';
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            async (position) => {
                const {latitude, longitude, accuracy} = position.coords;
                try {
                    console.log(latitude);
                    console.log(longitude);
                    const resultData = await fetch(`/customer/stores/search/distance?lat=${latitude}&lnt=${longitude}`);
                    if (!resultData.ok) {
                        console.log('에러발생');
                    }

                    const resultHTML = await resultData.text();

                    document.getElementById('loading').style.display = 'none';
                    document.getElementById('dynamicContainer').innerHTML = resultHTML;

                    // document.getElementById('resultBox').innerHTML = resultHTML;
                } catch (error) {
                    console.log('에러발생2') // todo
                }

            },
            (error) => {
                status = `위치 정보를 가져올 수 없습니다: ${error.message}`;
                document.getElementById('loading').style.display = 'none';
                document.getElementById('resultBox').innerHTML = status;
            },
            {
                enableHighAccuracy: true, // 정확도 우선 모드
                timeout: 10000,           // 10초 이내에 응답 없으면 에러 발생
                maximumAge: 0             // 항상 최신 위치 정보 수집
            }
        );
    } else {
        status = "브라우저가 위치 서비스를 지원하지 않습니다.";
    }
}


// DOM 비우기
function clearInner(dom, newDom) {
    dom.innerHTML = '';
    dom.innerHTML = newDom;
}