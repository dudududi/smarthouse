<?php

use Illuminate\Database\Seeder;

class UserTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
$dormek=0;
    	for($i=0;$i<100;$i++){
            $dormek=rand(1,14);
			 DB::table('users')->insert([
            'name' => str_random(10),
            'email' => str_random(10).'@gmail.com',
            'password' => bcrypt('secret'),
            'dorm_id' => $dormek,

        ]);
            DB::table('printers')->insert([

            'printer_model'=> 'Drukarka Model '.($i),
            'user_id'=> $i+1,
            'dorm_id'=>$dormek
            
            ]);
    	}

        
    }
}
