# News_Application

Problem statement
Develop an android application that meets the following requirements :
App requirements:
1. Has three screens :
a. Login screen with two fields - Username and password
b. News dashboard - Contains a list of news articles
a. A details screen showing the content of the selected news along with other data.
2. The user can like or unlike (if liked) an article either from the dashboard or from
the details screen of the article.
3. The user can view the details of an article by clicking on it.
4. The article is Text-only if no image is available.
5. The navigation of the app is as follows -
a. After Login, the dashboard screen is shown
b. The user can navigate between the dashboard and details screen
6. UI/UX is up to your liking. It should be fairly neat, with appropriate paddings and
margins.
Tech requirements
1. Must be written in Kotlin.
2. App has to have an architecture (preferrably MVVM)
3. If possible, make use of constraint-layout, databinding and live-data.

APIs
1. Login GET request - https://api.npoint.io/0774724810730d4ee184
a. POST request are not allowed by the domain, so no need to send `username`
and `password` in the body.

2. News api - https://api.npoint.io/7c27fa874f0a4d46e4d4
