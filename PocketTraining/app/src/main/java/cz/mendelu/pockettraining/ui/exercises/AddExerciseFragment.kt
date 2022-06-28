package cz.mendelu.pockettraining.ui.exercises

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import cz.mendelu.pockettraining.architecture.BaseFragment
import cz.mendelu.pockettraining.databinding.FragmentAddExerciseBinding
import kotlinx.coroutines.launch


class AddExerciseFragment : BaseFragment<FragmentAddExerciseBinding, AddExerciseViewModel>(
    AddExerciseViewModel::class) {

private val arguments: AddExerciseFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentAddExerciseBinding
        get() = FragmentAddExerciseBinding::inflate

    override fun initViews() {
        viewModel.id = if(arguments.id != -1L) arguments.id
        else null
        if(viewModel.id == null){
            fillLayout()
        } else{
            lifecycleScope.launch {
                viewModel.exercise = viewModel.findExerciseById()
            }.invokeOnCompletion {
                fillLayout()
            }
        }
        setInteractionListeners()
    }
    private fun fillLayout(){
        if(viewModel.exercise.exerciseName.isNotEmpty()){
            binding.exerciseName.text = viewModel.exercise.exerciseName
        }

        if(!viewModel.exercise.description.isNullOrEmpty()){
            binding.exerciseDescription.text = viewModel.exercise.description!!
        }
    }

    private fun setInteractionListeners(){
        binding.exerciseName.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.exercise.exerciseName = p0.toString()
            }
        })
        binding.exerciseDescription.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.exercise.description = p0.toString()
            }

        })

        binding.saveExerciseButton.setOnClickListener {
            if(binding.exerciseName.text.isNotEmpty()&&
                binding.exerciseName.text.length < 30 &&
                binding.exerciseDescription.text.length < 500){
                binding.exerciseName.setError(null)
                lifecycleScope.launch{
                    viewModel.saveExercise()
                }.invokeOnCompletion {
                    finishCurrentFragment()
                }
            }
            if(binding.exerciseName.text.isEmpty()){
                binding.exerciseName.setError("Cannot be empty")
            }
            if(binding.exerciseName.text.length >= 30){
                binding.exerciseName.setError("Text must be shorter than 30")
            }
            if(binding.exerciseDescription.text.length >= 500){
                binding.exerciseDescription.setError("Text must be shorter than 500")
            }
        }


        binding.fabDeleteExercise.setOnClickListener {
            if (arguments.id == -1L){
                binding.fabDeleteExercise.visibility = View.GONE
            }else{
                binding.fabDeleteExercise.visibility = View.VISIBLE
                lifecycleScope.launch{
                    viewModel.deleteExercise()
                }.invokeOnCompletion {
                    finishCurrentFragment()
                }}
        }
    }

    override fun onActivityCreated() {

    }

}