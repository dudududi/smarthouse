<?php

/*
|--------------------------------------------------------------------------
| Routes File
|--------------------------------------------------------------------------
|
| Here is where you will register all of the routes in an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
   return ('NIC TU NIE MA BO CISNIEMY IO');
});



Route::group(['prefix'=>'api'],function(){

Route::resource('authenticate', 'AuthenticateController', ['only' => ['index']]);
    Route::get('authenticate/user', 'AuthenticateController@getAuthenticatedUser');


Route::post('authenticate','AuthenticateController@authenticate');
Route::resource('rooms.temperature','TemperatureController',['only' => ['index','store']]);

Route::resource('rooms','RoomController',['only' => ['index']]);

Route::resource('rooms.doors','DoorController',['only' => ['index','store']]);

Route::resource('rooms.lights','LightController',['only' => ['index','store']]);

Route::resource('rooms.windows','WindowController',['only'=>['index','store']]);

});



/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| This route group applies the "web" middleware group to every route
| it contains. The "web" middleware group is defined in your HTTP
| kernel and includes session state, CSRF protection, and more.
|
*/

Route::group(['middleware' => ['web']], function () {
    //

});
