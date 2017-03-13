'use strict';

var App = angular.module("userApp", [ 'ngRoute', 'ngResource' ])
					.config(function($routeProvider) {
							$routeProvider.when('/getUsers', {
													controller : 'ChatController',
													templateUrl : 'chat.html'
										});
					});


