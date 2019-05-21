target/quiescent.jar: src/**/*
	clj -A:jar

deploy: target/quiescent.jar
	mvn deploy:deploy-file -Dfile=target/quiescent.jar -DrepositoryId=clojars -Durl=https://clojars.org/repo -DpomFile=pom.xml

.PHONY: deploy
