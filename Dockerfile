# ======================
# Stage 1: Build
# ======================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml الأول لتحسين الكاش
COPY pom.xml .

# تحميل dependencies
RUN mvn dependency:go-offline

# نسخ باقي المشروع
COPY src ./src

# build jar
RUN mvn clean package -DskipTests

# ======================
# Stage 2: Run
# ======================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# نسخ ال jar من ال stage الأول
COPY --from=builder /app/target/*.jar app.jar

# البورت (انت قلت 8084)
EXPOSE 8084

# تشغيل التطبيق
ENTRYPOINT ["java", "-jar", "app.jar"]