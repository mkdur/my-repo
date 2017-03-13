var app=angular.module('single-page-app',['ngRoute']);


app.config(function($routeProvider){


      $routeProvider
          .when('/',{
                templateUrl: 'home.html'
          })
		  .when('/register.html',{
                templateUrl: 'register.html'
          })
          .when('/getUsers',{
                templateUrl: 'about.html'
          })
		  .when('/receive',{
                templateUrl: 'chatWindow.html'
          });


});


app.controller('cfgController', ['$scope', '$routeParams' ,'UserService', function($scope,$routeParams, UserService ) {
    var self = this;
    self.user={id:null,name:'',password:'',email:''};
    self.users=[];

	self.chatMsg={receiver:'',message:'',author:'',chatName:'',id:null,chatTime:''};
	self.chats=[];
	
    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
	self.currentUser = '';

    //fetchAllUsers();
	
	self.checkCurrentUser = function(chatUserName){
		if(chatUserName !== self.currentUser)
			return true;
		else
			return false;
	}
	self.chat = function(tarUser,c){
		getChatFunc(tarUser,c);
	}
	
	setInterval(fetchAllUsers(self.currentUser), 5000);
	setTimeout(getChatFunc(self.targetUser,self.currentUser), 5000);
	window.setInterval(function() {

    window.location.assign(window.location.pathname + window.location.hash);

}, 15000); 
	
	
	function fetchCurrentChat(sc){
		sc.chat(self.targetUser);
	}
	
	
	function getChatFunc(tarUser,c){
			if(tarUser == null){
				tarUser = parse("s_n",true);
			}
			self.targetUser = tarUser;
			var curUser='';
			if(self.currentUser == ""){
				self.currentUser = parse("s_n",false);
			}
			if(self.currentUser === "" || tarUser === "")
				return;
				if(self.currentUser ===  tarUser){
					window.location.assign(window.location.pathname + window.location.hash);
					return;
				}
              UserService.getChatFunc(self.currentUser , tarUser)
            .then(
            function(d) {
                self.chats = d;
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }
	
    function fetchAllUsers(cUser){
        UserService.fetchAllUsers(cUser)
            .then(
            function(d) {
                self.users = d;
				self.currentUser = parse('s_n',false);
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }

    function createUser(user){
        UserService.createUser(user)
            .then(
            fetchAllUsers(user.name),
            function(errResponse){
                console.error('Error while creating User');
            }
        );
		
		self.currentUser = user.name;
    }

    function updateUser(user, id){
        UserService.updateUser(user, id)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while updating User');
            }
        );
    }

    function deleteUser(id){
        UserService.deleteUser(id)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while deleting User');
            }
        );
    }

    function submit() {
        if(self.user.id===null){
            console.log('Saving New User', self.user);
            createUser(self.user);
        }else{
            updateUser(self.user, self.user.id);
            console.log('User updated with id ', self.user.id);
        }
        reset();
    }

	self.send = function() {
			self.chatMsg.author = self.currentUser;
			self.chatMsg.receiver = self.targetUser;
			var d = new Date();
			var n = d.getTime();
			self.chatMsg.id = n;
			if(self.chatMsg.author === self.chatMsg.receiver)
				return;
            UserService.sendChat(self.chatMsg,self.targetUser,self.currentUser)
            .then(
            this.chat(self.targetUser),
            function(errResponse){
                console.error('Error while creating User');
            }
        );
        //self.chat(self.targetUser);
        reset();
    }
    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.users.length; i++){
            if(self.users[i].id === id) {
                self.user = angular.copy(self.users[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.user.id === id) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteUser(id);
    }


    function reset(){
        self.user={id:null,username:'',address:'',email:''};
		document.getElementsByName("sendForm")[0].reset();
        //$scope.myForm.$setPristine(); //reset Form
    }

	function parse(url,lastN){
		var a = window.location.hash;
		var lenA = a.length;
		var ret = '';
		
		var b =a.match(url);
		if(b === null)
			return '';
		var end = lenA;
		var andLoc =a.match(':');
		
		var start = b.index+url.length+1;
		
		if(andLoc !== null){
			if(lastN){
				start = andLoc.index+1;
				end = lenA;
			}else{			
				end = andLoc.index;
			}
		}
		if(b !== null){
			ret = a.substring(start , end);
		}
		return ret;
	}
}]);
