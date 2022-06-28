package cz.mendelu.pockettraining.ui.plans

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import cz.mendelu.pockettraining.architecture.BaseFragment
import cz.mendelu.pockettraining.databinding.FragmentExerciseToPlanBinding
import kotlinx.coroutines.launch

class ExerciseToPlanFragment :
BaseFragment<FragmentExerciseToPlanBinding, ExerciseToPlanViewModel>(ExerciseToPlanViewModel::class){
    private val arguments: ExerciseToPlanFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentExerciseToPlanBinding
        get() = FragmentExerciseToPlanBinding::inflate

    override fun initViews() {
        viewModel.id = if(arguments.id != -1L) arguments.id
        else null
        viewModel.exercise.fromPlanId = arguments.idPlan

        if(viewModel.id == null){
            binding.fabDeletePlan.visibility = View.GONE
            fillLayout()
        } else{
            lifecycleScope.launch {
                viewModel.exercise = viewModel.findUsedExerciseById()
            }.invokeOnCompletion {
                fillLayout()
            }
        }
        setInteractionListeners()
    }
    private fun fillLayout(){



        if(viewModel.exercise.usedExerciseName.isNotEmpty()){
            binding.exerciseName.text = viewModel.exercise.usedExerciseName
        }
        if (viewModel.exercise.reps == null){
            binding.exerciseReps.text = ""
        }else if(viewModel.exercise.reps.toString().isNotEmpty()){
            binding.exerciseReps.text = viewModel.exercise.reps.toString()

        }
        if (viewModel.exercise.sets == null){
            binding.exerciseSets.text = ""
        } else if(viewModel.exercise.sets.toString().isNotEmpty()){
            binding.exerciseSets.text = viewModel.exercise.sets.toString()
        }
        if (viewModel.exercise.pause == null){
            binding.exercisePauses.text = ""
        }
        else if(viewModel.exercise.pause.toString().isNotEmpty()){
            binding.exercisePauses.text = viewModel.exercise.pause.toString()
        }
    }
    private fun setInteractionListeners() {
        binding.exerciseName.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.exercise.usedExerciseName = p0.toString()
            }
        })


        fun isNumber(s: String?): Boolean {
            return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
        }
        binding.exerciseReps.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(isNumber(p0.toString()) && p0 != null) {
                    viewModel.exercise.reps = p0.toString().toInt()
                }
            }
        })
        binding.exerciseSets.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(isNumber(p0.toString()) && p0 != null) {
                    viewModel.exercise.sets = p0.toString().toInt()
                }
            }
        })
        binding.exercisePauses.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(isNumber(p0.toString()) && p0 != null) {
                    viewModel.exercise.pause = p0.toString().toLong()
                }
            }
        })

        binding.saveExerciseButton.setOnClickListener {
            fun isNumber(s: String?): Boolean {
                return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
            }
            viewModel.exercise.fromPlanId = arguments.idPlan
            //name
            if(binding.exerciseName.text.isEmpty()){
                binding.exerciseName.setError("Cannot be empty")
            }
            else{
                binding.exerciseName.setError(null)
            }
            if(binding.exerciseName.text.length >= 30){
                binding.exerciseName.setError("Text must be shorter than 30")
            }
            //pause


            if(isNumber(binding.exercisePauses.text)){
                binding.exercisePauses.setError(null)
            }else{
                binding.exercisePauses.setError("must be number")
            }
            if(binding.exercisePauses.text.isEmpty()){
                binding.exercisePauses.setError("Cannot be empty")
            }
            else{
                //binding.exercisePauses.setError(null)
            }
            if(binding.exercisePauses.text.length > 4){
                binding.exercisePauses.setError("Text must be shorter than 5")
            }
            //reps

            if(isNumber(binding.exerciseReps.text)){
                binding.exerciseReps.setError(null)
            }else{
                binding.exerciseReps.setError("must be number")
            }
            if (binding.exerciseReps.text.isEmpty()){
                binding.exerciseReps.setError("Cannot be empty")
            }else{
                //binding.exerciseReps.setError(null)
            }
            if(binding.exerciseReps.text.length > 3){
                binding.exerciseReps.setError("Text must be shorter than 4")
            }

            //sets


            if(isNumber(binding.exerciseSets.text)){
                binding.exerciseSets.setError(null)
            }else{
                binding.exerciseSets.setError("must be number")
            }
            if(binding.exerciseSets.text.isEmpty()){
                binding.exerciseSets.setError("Cannot be empty")
            }else{
                //binding.exerciseSets.setError(null)
            }
            if(binding.exerciseSets.text.length > 3){
                binding.exerciseSets.setError("Text must be shorter than 4")
            }

            if(binding.exerciseName.text.isNotEmpty() &&
                binding.exercisePauses.text.isNotEmpty() &&
                binding.exerciseReps.text.isNotEmpty() &&
                binding.exerciseSets.text.isNotEmpty()&&
                isNumber(binding.exercisePauses.text) &&
                isNumber(binding.exerciseReps.text) &&
                isNumber(binding.exerciseSets.text) &&
                (binding.exerciseName.text.length < 30)&&
                (binding.exerciseReps.text.length <= 3)&&
                (binding.exerciseSets.text.length <= 3)&&
                (binding.exercisePauses.text.length <= 4)
                    )
                {
                    lifecycleScope.launch{
                        viewModel.saveExercise()
                    }.invokeOnCompletion {
                        finishCurrentFragment()
                    }
                }
        }
        binding.fabDeletePlan.setOnClickListener {
            if (arguments.idPlan == -1L){
                binding.fabDeletePlan.visibility = View.GONE
            }else{
                binding.fabDeletePlan.visibility = View.VISIBLE
                lifecycleScope.launch{
                    viewModel.deleteUsedExercise()
                }.invokeOnCompletion {
                    finishCurrentFragment()
            }}
        }
    }

    override fun onActivityCreated() {

    }
}