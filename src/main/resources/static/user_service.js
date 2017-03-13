'use strict';

angular.module('single-page-app').factory('UserService', ['$http', '$q', function($http, $q){

    var REST_GET_USERS = 'getUsers';

	var REST_SIGNUP = 'signUp';
	var REST_GET_CHAT = 'receive';
	var REST_SEND_CHAT = 'send';
	
	var CURRENT_USER = '';
    var factory = {
        fetchAllUsers: fetchAllUsers,
        createUser: createUser,
        updateUser:updateUser,
        deleteUser:deleteUser,
		getChatFunc:getChatFunc,
		sendChat: sendChat
    };

    return factory;

	function sendChat(chat, targetUser , cUser){
		 var deferred = $q.defer();
        $http.post(REST_SEND_CHAT,chat)
            .then(
            function (response) {
                deferred.resolve(response.data);
				window.location.assign("index.html#!/receive?s_n="+cUser+":"+targetUser);
				//console.error('Current user : '+ CURRENT_USER);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
	}
	function getChatFunc(cuser,tuser){
       
               var deferred = $q.defer();
			   
        $http.get(REST_GET_CHAT+'/'+cuser+'/'+tuser)
            .then(
            function (response) {
                deferred.resolve(response.data);
				window.location.assign("index.html#!/receive?s_n="+cuser+":"+tuser);
				//console.error('Current user : '+ CURRENT_USER);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    function fetchAllUsers(cuser) {
        var deferred = $q.defer();
		var qry = '';
		if(cuser !== null && cuser !== '')
			qry = '&s_n=';
        $http.get(REST_GET_USERS+qry+cuser)
            .then(
            function (response) {
                deferred.resolve(response.data);
				//console.error('Current user : '+ CURRENT_USER);
				window.location.assign(window.location.pathname + window.location.hash);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function createUser(user) {
        var deferred = $q.defer();
        $http.post(REST_SIGNUP, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
				if(response.data == false){
					console.error('Invalid credentials User');
					window.location.assign("index.html?error=invalidCredentials");
					deferred.reject(errResponse);
				}else{
					CURRENT_USER = user.name;
					window.location.assign("index.html#!/getUsers?s_n="+user.name);
				}
            },
            function(errResponse){
                console.error('Error while creating User');
				window.location.assign("index.html?error=Error");
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updateUser(user, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteUser(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

	
}]);
