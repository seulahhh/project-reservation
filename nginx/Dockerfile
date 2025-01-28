# NginX의 기본 이미지의 conf 파일만 바꿔서 이미지를 재구성 한후 컨테이너를 띄울것임

FROM nginx:latest

# copy: local에서 docker쪽으로 복사해서 넘겨줌
COPY default.conf /etc/nginx/conf.d/default.conf

# nginx가 포그라운드에서 실행되게 해줌 (기본적으로 nginx는 background에서 실행되는데, 도커 컨테이너는 백그라운드에서 실행되게 되면 프로세스 종료의 염려가 있음)
CMD ["nginx", "-g", "daemon off;"]
# CMD:  Docker 이미지를 빌드한 후, 해당 이미지로 컨테이너를 실행할 때 CMD로 지정된 명령어가 자동으로 실행

## 이 파일을 이미지화 해야함r
## bash에서
# docker build -t nginxtest:1.0 .
# --> image 생성 완료
# 해당 이미지를 통해 docker container를 띄워야함
# docker images > 도커 이미지들 검색
# docker run nginxtest:1.0 하면 도커 실행 완료