**Project Name:**

ATIPERA RECRUITMENT TASK

**Description:**

A Spring Boot application that retrieves public GitHub repositories for a given username, along with basic information about their branches.

**Tech Stack:**

* Java 21
* Spring Boot 3.2.2
* WebClient
* Lombok

**Features:**

* Retrieves a list of public GitHub repositories for a given username.
* Filters out forked repositories.
* Fetches basic information about each repository's branches (name and last commit SHA).
* Handles user not found gracefully with a 404 response.
* Uses Accept header to validate input format.

**Usage:**

Build the project using your preferred method (e.g., Maven, Gradle). <br>
Run the application.<br>
Send a GET request to `/api/v1/repos?username=<username>`, setting the `"Accept"` header to `"application/json"`.
The response will be a `JSON` list of `GitHubRepoDTO` objects, containing information about the user's repositories and their branches.

Example Request: <br>
`GET /api/v1/repos?username=dawid-janta-lipinski` <br>
`Accept: application/json`


Example Response: <br>

```[
{
"repositoryName": "octocat/Hello-World",
"ownerLogin": "octocat",
"branches": [
{
"name": "main",
"lastCommitSha": "1234567890abcdef"
}
]
},
// ...other repositories
]
```


**Dependencies:**

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<optional>true</optional>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>

```